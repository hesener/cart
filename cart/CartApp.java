import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CartApp {
    public static void main(String[] args) {
        // 상품 목록 생성 및 CSV 파일에서 상품을 불러옴
        String fileName = "products.csv";
        Set<Product> productSet = loadProductsFromCSV(fileName);

        // 상품 목록 확인
        System.out.println("상품 목록:");
        for (Product product : productSet) {
            System.out.println(product.getName() + ", " + product.getPrice());
        }
        System.out.println();

        // 장바구니 생성
        Cart myCart = new Cart();

        // 상품을 장바구니에 추가
        myCart.addProduct(new Product(1, "우유", 2000), 1);

        // 상품을 장바구니에서 제거
        myCart.removeProduct(new Product(1, "우유", 2000), 1); // 제거할 때도 가격까지 명시해야 함

        // 장바구니에 현재 담긴 상품들 출력
        myCart.showItems();
    }

    private static Set<Product> loadProductsFromCSV(String fileName) {
        Set<Product> products = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int key = Integer.parseInt(data[0]); // 첫 번째 열은 key
                String name = data[1]; // 두 번째 열은 상품명
                int price = Integer.parseInt(data[2]);
                products.add(new Product(key, name, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}

class Product {
    private int key;
    private String name;
    private int price;

    public Product(int key, String name, int price) {
        this.key = key;
        this.name = name;
        this.price = price;
    }

    public int getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product)) return false;
        Product product = (Product) obj;
        return key == product.key && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, name);
    }
}

class Cart {
    private Map<Product, Integer> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void showItems() {
        if (items.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
        } else {
            System.out.println("장바구니에 현재 담긴 상품목록 (상품이름, 각 상품의 개수)");
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                System.out.println(entry.getKey().getName() + ": " + entry.getValue() + "개");
            }
        }
    }

    public void addProduct(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
        System.out.println(product.getName()+" " + quantity + "개가 장바구니에 추가되었습니다.");
        System.out.println();
    }

    public void removeProduct(Product product, int quantity) {
        int currentQuantity = items.getOrDefault(product, 0);
        if (currentQuantity == 0) {
            System.out.println("장바구니에 해당 상품이 존재하지 않습니다.");
        } else if (currentQuantity < quantity) {
            System.out.println("장바구니에 존재하는 상품의 수량을 초과하여 제거할 수 없습니다.");
        } else {
            items.put(product, currentQuantity - quantity);
            System.out.println(product.getName() + " " + quantity + "개가 장바구니에서 제거되었습니다.");
        }
        System.out.println();
    }
}
