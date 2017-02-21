

def power (x:Double, exp:Double): Double = {
  var r = 1.0
  var i = exp
  while (i >0) {r = r*x; i = i -1}
  r
}

power(2,3)


for (i <- 1 until 3) {println("AA " + i)}


for (i <- 1 until 3; j <- "abc") println(i + " " + j)
// translates to

(1 until 3) foreach(i => "abc" foreach (j => println(i+ " " +j)))