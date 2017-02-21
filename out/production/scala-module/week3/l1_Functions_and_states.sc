

def iterate(n: Int, f: Int => Int, x: Int) : Int = {
  if (n == 0) x else iterate(n - 1, f, f(x))
}

def square(x: Int) = x * x

iterate (1, square, 16)


class BankAccount {
  private var balance = 0
  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
  }

  def withdraw(amount: Int) : Int = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      balance
    } else throw new Error("Insuficient funds")
  }
}



val acct = new BankAccount
acct deposit 50
acct withdraw(20)
acct withdraw(100)


