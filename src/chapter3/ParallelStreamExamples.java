package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Item {
	private String name;
	private int weight;
	private Color color;
	public Item(String name, int weight, Color color) {
		this.name = name;
		this.weight = weight;
		this.color = color;
	}

	public static List<Item> createSamples(int number) {
		List<Item> result = new ArrayList<>();
		int redNumber = 0;
		int greenNumber = 0;
		int blueNumber = 0;
		for (int i = 0; i < number; i++) {
			int weight = (int) (Math.random() * 101);
			Color color;
			String name;
			double random = Math.random();
			if (random < 0.33) {
				color = Item.Color.RED;
				redNumber++;
				name = "Rot" + redNumber;
			} else if (random < 0.67) {
				color = Item.Color.GREEN;
				greenNumber++;
				name = "Gr�n" + greenNumber;
			} else {
				color = Item.Color.BLUE;
				blueNumber++;
				name = "Blau" + blueNumber;
			}
			result.add(new Item(name, weight, color));
		}
		return result;
	}

	public int getWeight() {
		return weight;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public enum Color {
		RED, GREEN, BLUE, YELLOW, BLACK, WHITE
	}
}

class SequentialBulkDataOperations {
	public static void execute1(List<Item> itemsList) {
		long start = System.currentTimeMillis();
		int sum = itemsList.stream().
				filter(p -> p.getColor() == Item.Color.RED).
				mapToInt(p -> p.getWeight()).
				sum();
		long end = System.currentTimeMillis();
		long time = end - start;

		System.out.println("Sequential sum computation " +
				"of red members: " +
				sum + " [in " + time / 1000.0 + " secs]");
	}

	public static void execute2(List<Item> itemsList) {
		long start = System.currentTimeMillis();
		Map<Item.Color, List<Item>> byColor =
				itemsList.stream().
						collect(Collectors.groupingBy(i -> i.getColor()));
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("Sequential grouping into " +
				byColor.size() + " groups " +
				"[in " + time / 1000.0 + " secs]");
	}
}

class ParallelBulkDataOperations {
	public static void execute1(List<Item> itemsList) {
		long start = System.currentTimeMillis();
		int sum = itemsList.parallelStream().
				filter(p -> p.getColor() == Item.Color.RED).
				mapToInt(p -> p.getWeight()).
				sum();
		long end = System.currentTimeMillis();
		long time = end - start;

		System.out.println("Parallel sum computation " +
				"of red members: " +
				sum + " [in " + time / 1000.0 + " secs]");
	}

	public static void execute2(List<Item> itemsList) {
		long start = System.currentTimeMillis();
		Map<Item.Color, List<Item>> byColor =
				itemsList.parallelStream().
						collect(Collectors.groupingByConcurrent(i -> i.getColor()));
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("Parallel grouping into " +
				byColor.size() + " groups " +
				"[in " + time / 1000.0 + " secs]");
	}
}

public class ParallelStreamExamples {
	private final static int MILLION = 1000 * 1000;

	private static void execute(int numberOfItems) {
		long start = System.currentTimeMillis();
		List<Item> itemsList = Item.createSamples(numberOfItems);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("Items list  with " + itemsList.size() +
				" elements created [in " +
				time / 1000.0 + " secs]");

		SequentialBulkDataOperations.execute1(itemsList);
		ParallelBulkDataOperations.execute1(itemsList);

		SequentialBulkDataOperations.execute2(itemsList);
		ParallelBulkDataOperations.execute2(itemsList);
	}

	public static void main(String[] args) {
		for (int i = 1; i <= 60; i++) {
			for (int j = 1; j <= 10; j++) {
				execute(i * MILLION);
				System.out.println("===================");
			}
		}
	}

	public static void main2(String[] args) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			list.add(i);
		}
		long start, end, time;

		start = System.currentTimeMillis();
		list.parallelStream().
				peek(e -> {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
					}
				}).
				forEach(e -> System.out.println(e));
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println(time / 1000.0 + " secs");
		System.out.println("======================");

		start = System.currentTimeMillis();
		list.parallelStream().filter(e -> e % 2 == 0).
				peek(e -> {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
					}
				}).
				forEach(e -> System.out.println(e));
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println(time / 1000.0 + " secs");
		System.out.println("======================");

		start = System.currentTimeMillis();
		list.parallelStream().
				peek(e -> {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
					}
				}).
				filter(e -> e % 2 == 0).
				forEach(e -> System.out.println(e));
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println(time / 1000.0 + " secs");
		System.out.println("======================");
	}
}
