let a = 5;
let b = 3;
let c = 0;

print("a = " + a + ", b = " + b + ", c = " + 0);
print("----------------");

print(a + b * c);
print(a + c * b);
print((a + c) * b);
print(a + "c" + b * c);

print("----------------");
c = 2;
print("c is now 2");
print("----------------");

print(a + b * c);
print(a + c * b);
print((a + c) * b);
print(a + "c" + b * c);

print("now c is a string!");
c = "this wont work";
