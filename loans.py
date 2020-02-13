import matplotlib.pyplot as plt
from matplotlib.widgets import Slider, Button, CheckButtons
import copy


class Account:
    def __init__(self, p, r, n=1, t=1/365.25, is_loan=True):
        self.principal = p if is_loan else -p
        self.rate = r
        self.num_accrue_per_t = n
        self.time = t
        self.is_loan = is_loan
    
    def accrue(self):
        self.principal = calculate_interest( \
                self.principal if self.is_loan else -self.principal, \
                    self.rate, self.num_accrue_per_t, self.time)
        self.principal = self.principal if self.is_loan else -self.principal
    
    def pay(self, amount):
        self.principal += -amount if self.is_loan else amount
    
    def __str__(self):
        return str(self.principal)


def calculate_interest(p, r, n=1, t=1/365.25):
    return p*((1+(r/n))**(n*t))


"""Pay off the smallest principal first"""
def snowball_strategy(accounts, ammount, ignore_investing=True):
    loans = [account for account in accounts if account.is_loan] \
        if ignore_investing else accounts
    min(loans, key=lambda x:x.principal).pay(ammount)


"""Pay off the largest interest rate first"""
def avalanche_strategy(accounts, ammount, ignore_investing=True):
    loans = [account for account in accounts if account.is_loan] \
        if ignore_investing else accounts
    max(loans, key=lambda x:x.rate).pay(ammount)


"""Pay off the largest accrued interest first"""
def max_accrued_strategy(accounts, ammount, ignore_investing=True):
    loans = [account for account in accounts if account.is_loan] \
        if ignore_investing else accounts
    max(loans, key=lambda x:calculate_interest(x.principal, x.rate)) \
        .pay(ammount)


"""Pay off each loan proportional to their interest rate"""
def prop_to_rate_strategy(accounts, ammount, ignore_investing=True):
    loans = [account for account in accounts if account.is_loan] \
        if ignore_investing else accounts
    summed_rates = sum([loan.rate for loan in loans])
    [loan.pay(ammount*(loan.rate/summed_rates)) for loan in loans]


"""Pay off each loan proportional to their principal"""
def prop_to_principal_strategy(accounts, ammount, ignore_investing=True):
    loans = [account for account in accounts if account.is_loan] \
        if ignore_investing else accounts
    summed_p = sum([loan.principal for loan in loans])
    [loan.pay(ammount*(loan.principal/summed_p)) for loan in loans]


"""Pay off each loan proportional to their accrued interest"""
def prop_to_accrued_strategy(accounts, ammount, ignore_investing=True):
    loans = [account for account in accounts if account.is_loan] \
        if ignore_investing else accounts
    accrued_interests = [calculate_interest(loan.principal, loan.rate) \
        for loan in loans]
    summed_accrued_interest = sum(accrued_interests)
    for loan in loans:
        accrued_interest = calculate_interest(loan.principal, loan.rate)
        proportion = accrued_interest/summed_accrued_interest
        loan.pay(ammount*proportion)


strategies = [snowball_strategy, avalanche_strategy, max_accrued_strategy, \
    prop_to_rate_strategy, prop_to_principal_strategy, \
        prop_to_accrued_strategy]


def test_all_strategies(original_accounts, payday=1000, ignore_investing=True):
    for strategy in strategies:
        strategy_results = test_strategy(original_accounts, strategy, \
            payday, ignore_investing)
        plt.plot(strategy_results, label=strategy.__name__)
    plt.xlabel('Days')
    plt.ylabel('Debt')
    plt.legend()
    plt.show()


def test_strategy(original_accounts, strategy, \
    payday=1000, freq=14, ignore_investing=True):
    accounts = copy.deepcopy(original_accounts)
    days = []
    while len([account for account in accounts if account.is_loan]) > 0 and \
        2*10**5 > sum([a.principal for a in accounts]):
        days.append(sum([account.principal for account in accounts]))
        if len(days)%freq == 0:
            strategy(accounts, payday, ignore_investing)
            accounts = [a for a in accounts if a.principal > 0]
        [account.accrue() for account in accounts]
    return days


def days_until_freedom(accounts, strategy, payday=1000):
    return len(test_strategy(accounts, strategy, payday))


def interactive_strategy_comparison(original_accounts, ignore_investing=True):
    fig, ax = plt.subplots()
    plt.xlabel('Days')
    plt.ylabel('Debt')
    plt.subplots_adjust(left=0.25, bottom=0.25)
    lines = dict()
    for strategy in strategies:
        strategy_results = test_strategy(original_accounts, strategy, 200, \
            14, ignore_investing)
        l, = plt.plot(strategy_results, label=strategy.__name__)
        lines[strategy.__name__] = l
    
    axfreq = plt.axes([0.25, 0.1, 0.65, 0.03])
    axpayday = plt.axes([0.25, 0.15, 0.65, 0.03])
    freq = Slider(axfreq, 'Pay Freq', 0, 30, valinit=14, valstep=1)
    payday = Slider(axpayday, 'Payday', 0, 5000, valinit=200, valstep=50)
    
    def update(val):
        max_x_value = 0
        max_y_value = 0
        for strategy in strategies:
            s_results = test_strategy(original_accounts, strategy, \
                payday.val, freq.val, ignore_investing)
            lines[strategy.__name__].set_ydata(s_results)
            
            if max(s_results) * 1.05 > max_y_value:
                max_y_value = max(s_results) * 1.05
            max_y_value = min(2*10**6, max_y_value)
            
            if len(s_results) * 1.05 > max_x_value:
                max_x_value = len(s_results) * 1.05
            
            x_data = list(range(len(s_results)))
            lines[strategy.__name__].set_xdata(x_data)
        
        ax.set_xbound(lower=0, upper=max_x_value)
        ax.set_ybound(lower=0, upper=max_y_value)
        fig.canvas.draw_idle()
    
    freq.on_changed(update)
    payday.on_changed(update)
    
    resetax = plt.axes([0.8, 0.025, 0.1, 0.04])
    button = Button(resetax, 'Reset')
    
    def reset(event):
        payday.reset()
    
    button.on_clicked(reset)
    
    cax = plt.axes([0.015, 0.35, 0.15, 0.4])
    check = CheckButtons(cax, [s.__name__[:-9] for s in strategies], \
        [True]*len(strategies))
    
    def choose_strategy(label):
        for key in lines:
            if label in key:
                lines[key].set_visible(not lines[key].get_visible())
        max_x_value = max([max(line.get_xdata()) for line in lines.values() \
            if line.get_visible()])
        max_y_value = max([max(line.get_ydata()) for line in lines.values() \
            if line.get_visible()])
        ax.set_xbound(lower=0, upper=max_x_value)
        ax.set_ybound(lower=0, upper=max_y_value)
        plt.draw()
    
    check.on_clicked(choose_strategy)
    
    plt.show()


def main():
    accounts = []
    accounts.append(Account(50000, 0.05))
    accounts.append(Account(20000, 0.08))
    accounts.append(Account(10000, 0.02))
    interactive_strategy_comparison(accounts, ignore_investing=False)


if __name__ == "__main__":
    main()
