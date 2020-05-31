package chapter3;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
	public static String supply() {
		return apply("");
	}

	public static String apply(String message) {
		int sleepTime = (int) (Math.random() * 4000 + 1000);
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
//        if(Math.random() < 0.5)
//        {
//            throw new IllegalArgumentException();
//        }
		String result = message;
		if (result.length() > 0) {
			result += " - ";
		}
		result += sleepTime + " [" + Thread.currentThread().getName() + "]";
		return result;
	}

	public static String combine(String message1, String message2) {
		String result = "(" + message1 + "\n" +
				" | combined " +
				" [" + Thread.currentThread().getName() + "] |\n" +
				message2 + ")";
		return result;
	}

	public static void consume(String message) {
		System.out.println(apply(message));
	}

	private static void timedExecution(Runnable r) {
		long start = System.currentTimeMillis();
		r.run();
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println("Total time: " + total);
		System.out.println("====================================");
	}

	public static void main(String[] args) {
		timedExecution(() -> example1());
		timedExecution(() -> example2());
		timedExecution(() -> example3());
		timedExecution(() -> example4());
		timedExecution(() -> example5());
		timedExecution(() -> example6());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
	}

	private static void example1() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> supply());
		CompletableFuture<String> f2 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f3 = f2.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f4 = f3.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f5 = f4.thenApplyAsync(s -> apply(s));

		System.out.println(f5.join());
	}

	private static void example2() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> supply());
		CompletableFuture<String> f2 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f3 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f4 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f23 = f2.thenCombineAsync(f3, (s2, s3) -> combine(s2, s3));
		CompletableFuture<String> f234 = f23.thenCombineAsync(f4, (s23, s4) -> combine(s23, s4));
		CompletableFuture<String> f5 = f234.thenApplyAsync(s -> apply(s));

		System.out.println(f5.join());
	}

	private static void example3() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> supply());
		CompletableFuture<String> f2 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f3 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f23 = f2.applyToEitherAsync(f3, s -> apply(s));
		CompletableFuture<String> f4 = f23.thenApplyAsync(s -> apply(s));

		System.out.println(f4.join());
	}

	private static void example4() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> supply());
		CompletableFuture<String> f2 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f3 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<String> f4 = f1.thenApplyAsync(s -> apply(s));
		CompletableFuture<Object> f234 = CompletableFuture.anyOf(f2, f3, f4);
		CompletableFuture<String> f5 = f234.thenApplyAsync(s -> (String) apply((String) s));

		System.out.println(f5.join());
	}

	private static void example5() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> supply());
		CompletableFuture<Void> f2 = f1.thenApplyAsync(s -> apply(s)).thenAcceptAsync(s -> consume(s));
		CompletableFuture<Void> f3 = f1.thenApplyAsync(s -> apply(s)).thenAcceptAsync(s -> consume(s));
		CompletableFuture<Void> f4 = f1.thenApplyAsync(s -> apply(s)).thenAcceptAsync(s -> consume(s));

		CompletableFuture<Void> done = CompletableFuture.allOf(f2, f3, f4);
		Void v = done.join();
		System.out.println("All done (" + v + ")");
	}

	private static void example6() {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> supply());
		CompletableFuture<Void> f2 = f1.thenApplyAsync(s -> apply(s)).thenAcceptAsync(s -> consume(s));
		CompletableFuture<Void> f3 = f1.thenApplyAsync(s -> apply(s)).thenAcceptAsync(s -> consume(s));
		CompletableFuture<Void> f4 = f1.thenApplyAsync(s -> apply(s)).thenAcceptAsync(s -> consume(s));

		CompletableFuture<Object> done = CompletableFuture.anyOf(f2, f3, f4);
		Object o = done.join();
		System.out.println("Any done (" + o + ")");
	}
}
