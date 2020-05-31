package chapter3;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

class Class1 {
}

class Class2 {
}

class Class2Extended extends Class2 {
}

class Class3 {
}

class Class4 {
}

class Class5 {
}

public class CompletableFutureDemo2 {
	public static void main(String[] args) throws Exception {
		Supplier<Class1> job1 = () -> new Class1();
		Function<Object, Class2Extended> job2 = o -> new Class2Extended();
		Function<Class2, Class3> job3 = c1 -> new Class3();
		Function<Class3, Class4> job4 = c1 -> new Class4();
		Function<Class4, Class5> job5 = c1 -> new Class5();
		Class5 result = CompletableFuture.supplyAsync(job1).
				thenApplyAsync(job2).
				thenApplyAsync(job3).
				thenApplyAsync(job4).
				thenApplyAsync(job5).
				get();
		System.out.println(result);
	}
}
