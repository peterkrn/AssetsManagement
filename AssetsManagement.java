import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Scanner;
import java.text.SimpleDateFormat;

class Warehouse {
    public String name;
    public Branch branch;
    public HashMap<Asset, Integer> assets;

    public Warehouse(String name, Branch branch) {
        this.name = name;
        this.branch = branch;
        this.assets = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Branch getBranch() {
        return branch;
    }

    public HashMap<Asset, Integer> getAssets() {
        return assets;
    }

    public void addAsset(Asset asset, Integer quantity) {
        assets.put(asset, quantity);
    }

    public void deleteAsset(Asset asset) {
        assets.remove(asset);
    }

    public Asset findAsset(String name) {
        for (Asset asset : assets.keySet()) {
            if (asset.getName().equalsIgnoreCase(name)) {
                return asset;
            }
        }
        return null;
    }
}

class Branch {
    public String name;
    public List<Warehouse> warehouses;

    public Branch(String name) {
        this.name = name;
        warehouses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }

    public void deleteWarehouse(Warehouse warehouse) {
        warehouses.remove(warehouse);
    }
}

class Asset {
    public String name;
    public String productCode;
    public String productID;
    public double price;
    public String merk;
    public String category;
    public String registeredDate;
    public List<AssetMovement> assetMovements;
    public List<AssetMovement> assetAdditions;

    public Asset(String name, String productCode, String productID, double price, String merk,
            String category, String registeredDate) {
        this.name = name;
        this.productCode = productCode;
        this.productID = productID;
        this.price = price;
        this.merk = merk;
        this.category = category;
        this.registeredDate = registeredDate;
        this.assetMovements = new ArrayList<>();
        this.assetAdditions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductID() {
        return productID;
    }

    public double getPrice() {
        return price;
    }

    public String getMerk() {
        return merk;
    }

    public String getCategory() {
        return category;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void addAssetMovement(AssetMovement assetMovement) {
        assetMovements.add(assetMovement);
    }

    public List<AssetMovement> getAssetMovements() {
        return assetMovements;
    }
}

class AssetMovement {
    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Warehouse sourceWarehouse;
    public Warehouse destinationWarehouse;
    public int quantity;
    public String date;

    public AssetMovement(Warehouse source, Warehouse destination, int quantity) {
        this.destinationWarehouse = destination;
        this.sourceWarehouse = source;
        this.quantity = quantity;
        this.date = formatter.format(new Date());
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }
}

public class AssetsManagement {

    static class Admin {
        String ID;
        String password;

        public Admin(String ID, String password) {
            this.ID = ID;
            this.password = password;
        }

        public Admin() {
            this.ID = "admin";
            this.password = "admin";
        }

        public String getID() {
            return ID;
        }

        public String getPassword() {
            return password;
        }

    }

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    static Scanner scanner = new Scanner(System.in);

    public List<Branch> branches;

    public AssetsManagement() {
        branches = new ArrayList<>();
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public void deleteBranch(Branch branch) {
        branches.remove(branch);
    }

    public static String getLoginStatus(Admin admin) {
        int counter = 0;
        String loginStatus = "";
        do {
            System.out.print("ID: ");
            String ID = scanner.next().toLowerCase(Locale.ENGLISH);
            System.out.print("Password: ");
            String password = scanner.next().toLowerCase(Locale.ENGLISH);
            if (ID.equalsIgnoreCase(admin.getID()) && password.equalsIgnoreCase(admin.getPassword())) {
                loginStatus = "success";
                counter = 3;
            } else {
                loginStatus = "failed";
                System.out.println("Login Gagal. Silahkan coba lagi.");
                counter++;
                if (counter >= 3) {
                    System.out.println("Percobaan Login mencapai batas. Program stopped.");
                    System.exit(0);
                }
            }
        } while (counter < 3);
        return loginStatus;
    }

    public static Branch findBranch(List<Branch> branches, String branchName) {
        for (Branch branch : branches) {
            if (branch.getName().equalsIgnoreCase(branchName)) {
                return branch;
            }
        }
        return null;
    }

    public static Warehouse findWarehouse(List<Branch> branches, String warehouseName) {
        for (Branch branch : branches) {
            for (Warehouse warehouse : branch.getWarehouses()) {
                if (warehouse.getName().equalsIgnoreCase(warehouseName)) {
                    return warehouse;
                }
            }
        }
        return null;
    }

    public static void displayAllAssets(List<Branch> branches) {// menu1
        System.out.println("\t\t\n----- Semua Asset -----\n");

        // Print table headers
        System.out.printf("%-10s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", "Cabang", "Warehouse",
                "Asset Name",
                "Category", "Merk", "Price", "Product Code", "Quantity", "Registered Date");

        for (Branch branch : branches) {
            for (Warehouse warehouse : branch.getWarehouses()) {
                for (var kv : warehouse.assets.entrySet()) {
                    var asset = kv.getKey();
                    var quantity = kv.getValue();
                    System.out.printf("%-10s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n",
                            branch.getName(), warehouse.getName(), asset.getName(), asset.getCategory(),
                            asset.getMerk(), asset.getPrice(), asset.getProductCode(), quantity,
                            asset.getRegisteredDate());
                }

            }
        }
        System.out.println("\n");
    }

    public static void displayBranchAssets(List<Branch> branches, Scanner scanner) {// menu2
        System.out.println("\n----- Assets pada Cabang Tertentu -----\n");
        System.out.print("Nama cabang: ");
        String branchName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        branchName = branchName.replace(" ", "");
        Branch branch = findBranch(branches, branchName);

        if (branch != null) {
            System.out.println(
                    "--------------------------------------------- " + branch.getName()
                            + " ---------------------------------------------");

            var totalMap = new HashMap<>(branch.warehouses.get(0).assets);
            for (var kv : totalMap.entrySet()) {
                totalMap.put(kv.getKey(), 0);
            }
            for (Warehouse warehouse : branch.getWarehouses()) {
                for (var kv : warehouse.getAssets().entrySet()) {
                    var quantity = totalMap.get(kv.getKey());
                    totalMap.put(kv.getKey(), quantity + kv.getValue());
                }
            }

            // Print table headers
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s%n",
                    "Asset Name", "Category", "Merk", "Price", "Product Code", "Quantity",
                    "Registered Date");

            for (var kv : totalMap.entrySet()) {
                var mergedAsset = kv.getKey();
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15d %-15s%n", mergedAsset.getName(),
                        mergedAsset.getCategory(), mergedAsset.getMerk(),
                        mergedAsset.getPrice(), mergedAsset.getProductCode(),
                        kv.getValue(), mergedAsset.getRegisteredDate());
            }

        } else {
            System.out.println("Cabang tidak ditemukan.");
        }
    }

    public static void displayWarehouseAssets(List<Branch> branches, Scanner scanner) {// menu3
        System.out.println("\n----- Semua Asset di Cabang dan Warehouse tertentu -----\n");
        System.out.print("Nama cabang: ");
        String branchName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        branchName = branchName.replace(" ", "");

        Branch branch = findBranch(branches, branchName);
        if (branch != null) {
            System.out.print("Nama warehouse: ");
            String warehouseName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
            warehouseName = warehouseName.replace(" ", "");

            Warehouse warehouse = findWarehouse(branches, warehouseName);
            if (warehouse != null) {
                System.out.println("Warehouse " + warehouse.getName() + " di " + branch.getName() + ":\n");

                // Print table headers
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %n",
                        "Asset Name", "Category", "Merk", "Price", "Product Code", "Quantity", "Registered Date");

                for (var kv : warehouse.getAssets().entrySet()) {
                    var asset = kv.getKey();
                    var quantity = kv.getValue();
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %n",
                            asset.getName(), asset.getCategory(), asset.getMerk(), asset.getPrice(),
                            asset.getProductCode(), quantity, asset.getRegisteredDate());

                }
            } else {
                System.out.println("Warehouse tidak ditemukan. Silahkan ulangi");
            }
        } else {
            System.out.println("Cabang tidak ditemukan.");
        }
        System.out.println("\n");
    }

    public static void transactionAssets(List<Branch> branches, Scanner scanner) { // menu4
        System.out.print("Nama cabang: ");
        String branchName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        branchName = branchName.replace(" ", "");

        Branch branch = findBranch(branches, branchName);
        if (branch != null) {
            System.out.print("Nama warehouse: ");
            String warehouseName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
            warehouseName = warehouseName.replace(" ", "");

            Warehouse warehouse = findWarehouse(branches, warehouseName);
            if (warehouse != null) {
                System.out.println("Pilih transaksi: "
                        + "\n1. Transfer Asset"
                        + "\n2. Beli Asset"
                        + "\n3. Jual Asset"
                        + "\n4. Tambah Asset Baru"
                        + "\n0. Keluar" + "\n==================" + "\nPilihan anda: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:// menu4.1
                        transferAsset(branches, scanner);
                        break;
                    case 2:// menu4.2
                        System.out.println("Nama Asset: ");
                        String assetName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
                        assetName = assetName.replace(" ", "");
                        Asset asset = warehouse.findAsset(assetName);

                        if (asset != null) {
                            addAssetQuantity(warehouse, asset, scanner); // menu4.2.1
                        } else {
                            System.out.println("Asset tidak ditemukan. Silakan tambah asset terlebih dahulu.");
                        }
                        break;
                    case 3: // menu4.3
                        System.out.println("Jual Asset");
                        System.out.println("Nama Asset: ");
                        assetName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
                        assetName = assetName.replace(" ", "");
                        asset = warehouse.findAsset(assetName);
                        reduceAssetQuantity(warehouse, asset, scanner);
                        break;
                    case 4: // menu4.4
                        System.out.println("Nama Asset: ");
                        assetName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
  
                        asset = warehouse.findAsset(assetName);
                        addAsset(branches, scanner);
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } else {
                System.out.println("Warehouse tidak ditemukan.");
            }
        } else {
            System.out.println("Cabang tidak ditemukan.");
        }

    }

    public static boolean transferAsset(List<Branch> branches, Scanner scanner) {// menu4.1
        System.out.println("\n----- Transfer Asset -----\n");
        System.out.print("Nama Asset: ");
        String assetName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        assetName = assetName.replace(" ", "");

        System.out.print("Asal Cabang: ");
        String sourceBranchName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        sourceBranchName = sourceBranchName.replace(" ", "");

        Branch sourceBranch = findBranch(branches, sourceBranchName);

        if (sourceBranch == null) {
            System.out.println("Asal Cabang " + sourceBranchName + " tidak ditemukan");
            return false;
        }

        System.out.print("Asal Warehouse: ");
        String sourceWarehouseName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        sourceBranchName = sourceBranchName.replace(" ", "");

        Warehouse sourceWarehouse = findWarehouse(branches, sourceWarehouseName);

        if (sourceWarehouse == null) {
            System.out.println("Asal Warehouse " + sourceWarehouseName + " tidak ditemukan");
            return false;
        }

        Asset asset = sourceWarehouse.findAsset(assetName);

        if (asset == null) {
            System.out.println("Asset " + assetName + " tidak ditemukan");
            return false;
        }

        System.out.print("Cabang Tujuan: ");
        String destinationBranchName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        destinationBranchName = destinationBranchName.replace(" ", "");

        Branch destinationBranch = findBranch(branches, destinationBranchName);

        if (destinationBranch == null) {
            System.out.println("Cabang Tujuan " + destinationBranchName + " tidak ditemukan");
            return false;
        }

        System.out.print("Nama Warehouse Tujuan : ");
        String destinationWarehouseName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        destinationWarehouseName = destinationWarehouseName.replace(" ", "");

        Warehouse destinationWarehouse = findWarehouse(branches, destinationWarehouseName);

        if (destinationWarehouse == null) {
            System.out.println("Warehouse Tujuan : " + destinationWarehouseName + " tidak ditemukan");
            return false;
        }

        if (destinationWarehouse == sourceWarehouse) {
            System.out.println("Tidak bisa memindahkan ke Warehouse yang sama!");
            return false;
        }

        System.out.print("Quantity yang akan dipindahkan : ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (quantity <= 0) {
            System.out.println("Quantity nol.");
        } else {
            var destinationQuantity = destinationWarehouse.assets.get(asset);
            var sourceQuantity = sourceWarehouse.assets.get(asset);
            if (sourceQuantity >= quantity) {
                destinationWarehouse.assets.put(asset, destinationQuantity + quantity);
                sourceWarehouse.assets.put(asset, sourceQuantity - quantity);

                System.out.println("Sukses memindahkan " + quantity + " " + assetName + " dari "
                        + sourceBranchName
                        + " - " + sourceWarehouseName + " ke " + destinationBranchName + " - "
                        + destinationWarehouseName);

                AssetMovement movement = new AssetMovement(sourceWarehouse, destinationWarehouse, quantity);

                asset.assetMovements.add(movement);

                return true;
            } else {
                System.out.println("Quantity yang akan dipindahkan melebihi Quantity yang ada");
                return false;
            }
        }
        return false;
    }

    public static void addAssetQuantity(Warehouse warehouse, Asset asset, Scanner scanner) { // menu4.2.1

        System.out.print("Jumlah yang akan dibeli : ");
        int amount = scanner.nextInt();
        scanner.nextLine();

        if (amount < 0) {
            throw new IllegalArgumentException("Jumlah tidak boleh negatif.");
        }

        int currentQuantity = warehouse.assets.get(asset);

        warehouse.assets.put(asset, currentQuantity + amount);

        System.out.println("Quantity dari " + asset.getName() + " telah ditambahkan sebanyak " + amount + ".");
    }

    public static void reduceAssetQuantity(Warehouse warehouse, Asset asset, Scanner scanner) { // menu4.3

        System.out.print("Jumlah yang akan dijual : ");
        int amount = scanner.nextInt();
        scanner.nextLine();

        if (amount < 0) {
            throw new IllegalArgumentException("Jumlah tidak boleh negatif.");
        }

        int currentQuantity = warehouse.assets.get(asset);

        if (amount > currentQuantity) {
            throw new IllegalArgumentException("Jumlah tidak boleh melebihi Quantity yang ada.");
        }

        warehouse.assets.put(asset, currentQuantity - amount);

        System.out.println("Quantity dari " + asset.getName() + " telah dijual sebanyak " + amount + ".");
    }

    public static void addAsset(List<Branch> branchesx, Scanner scanner) { // menu4.4
        System.out.println("----- Beli Asset Baru-----");
        System.out.print("Nama: ");
        String name = scanner.nextLine().toLowerCase(Locale.ENGLISH);
      

        System.out.print("Product Code: ");
        String productCode = scanner.nextLine().toLowerCase();
       

        System.out.print("Product ID: ");
        String productID = scanner.nextLine().toLowerCase(Locale.ENGLISH);
    

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        scanner.nextLine();

        System.out.print("Merk: ");
        String merk = scanner.nextLine().toLowerCase(Locale.ENGLISH);
       

        System.out.print("Category: ");
        String category = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        

        Date registeredDate = new Date();
        String dateString = formatter.format(registeredDate);

        Asset asset = new Asset(name, productCode, productID, price, merk, category, dateString);

        for (var branch : branchesx) {
            for (var warehouse : branch.warehouses) {
                warehouse.assets.put(asset, 0);
            }
        }
    }

    public static void displayAssetMovementIn(Asset asset) {
        System.out.println("===========================================");
        System.out.println("===========================================");
        System.out.println("Nama Asset: " + asset.getName());
        System.out.println("Registered Date: " + asset.getRegisteredDate());
        System.out.println("Category: " + asset.getCategory());
        System.out.println("Merk: " + asset.getMerk());
        System.out.println("Price: " + asset.getPrice());
        System.out.println("===========================================");

        for (var assetMovement : asset.assetMovements) {
            System.out.println("Movement Date: " + assetMovement.date);
            System.out.println("Quantity: " + assetMovement.quantity);
            if (assetMovement.sourceWarehouse != null) {
                System.out.println("Source: " + assetMovement.sourceWarehouse.getName());
            }
            if (assetMovement.destinationWarehouse != null) {
                System.out.println("Destination: " + assetMovement.destinationWarehouse.getName());
            }
            System.out.println("===========================================");
        }
    }

    public static void displayAllAssetMovement(List<Branch> branches) { // menu5
        System.out.println("\n----- All Asset Movement History -----\n");
        for (var asset : branches.get(0).warehouses.get(0).assets.keySet()) {
            displayAssetMovementIn(asset);
        }
    }

    public static void displayAssetMovement(List<Branch> branches, Scanner scanner) { // menu6
        System.out.println("\n----- Display Asset Movement History -----\n");

        System.out.print("Nama Asset: ");
        String assetName = scanner.nextLine().toLowerCase(Locale.ENGLISH);
        assetName = assetName.replace(" ", "");

        Asset foundAsset = null;
        for (Branch branch : branches) {
            for (Warehouse warehouse : branch.getWarehouses()) {
                Asset foundAssetx = warehouse.findAsset(assetName);
                if (foundAsset == null) {
                    foundAsset = foundAssetx;
                }
            }
        }
        if (foundAsset == null) {
            System.out.println("Asset " + assetName + " tidak ditemukan");
        } else {
            displayAssetMovementIn(foundAsset);
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Branch branch1 = new Branch("cabang1");
        Warehouse warehouse1 = new Warehouse("WH1-01", branch1);
        Warehouse warehouse2 = new Warehouse("WH1-02", branch1);
        branch1.addWarehouse(warehouse1);
        branch1.addWarehouse(warehouse2);

        Branch branch2 = new Branch("cabang2");
        Warehouse warehouse3 = new Warehouse("WH2-01", branch2);
        Warehouse warehouse4 = new Warehouse("WH2-02", branch2);
        branch2.addWarehouse(warehouse3);
        branch2.addWarehouse(warehouse4);

        Asset[] assets = new Asset[] {
                new Asset("Hammer", "PC001", "A001", 100000, "Krisbow", "Tools", "2015-05-14"),
                new Asset("Water Pump", "PC002", "A002", 100000, "Shimizu", "Machinery", "2014-07-20"),
                new Asset("Battery", "PC003", "A003", 120000, "ABC", "Electrical", "2017-01-10"),
                new Asset("Drill Machine", "PC004", "A004", 1500000, "Makita", "Tools", "2006-12-01"),
                new Asset("Generator Set", "PC005", "A005", 5000000, "Samsung", "Electrical", "2009-08-15"),
        };

        AssetsManagement assetManagement = new AssetsManagement();
        assetManagement.addBranch(branch1);
        assetManagement.addBranch(branch2);

        for (Branch branch : assetManagement.branches) {
            for (Warehouse warehouse : branch.getWarehouses()) {
                for (Asset asset : assets) {
                    warehouse.assets.put(asset, 0);
                }
            }
        }

        List<Branch> branches = assetManagement.getBranches();

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        System.out.println("Selamat datang di Warehouse Assets Management!");
        System.out.println("==============================================");
        System.out.println("Silakan login");
        Admin admin = new Admin();
        String loginStatus = getLoginStatus(admin);

        System.out.println("Cabang dan Warehouse yang Tersedia:");
        for (Branch branch : branches) {
            System.out.println("- " + branch.getName());
            for (Warehouse warehouse : branch.getWarehouses()) {
                System.out.println("   - " + warehouse.getName());
            }
        }
        System.out.println();

        while (loginStatus.equalsIgnoreCase("success") && choice != 0) {
            System.out.println("Menu: "
                    + "\n1. Tampilkan seluruh asset dari seluruh cabang"
                    + "\n2. Tampilkan seluruh asset dari cabang tertentu"
                    + "\n3. Tampilkan seluruh asset dari warehouse di cabang tertentu"
                    + "\n4. Transaksi asset"
                    + "\n5. Tampilkan history semua asset"
                    + "\n6. Tampilkan history sebuah asset"
                    + "\n0. Keluar" + "\n==================" + "\nPilihan anda: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAllAssets(branches);
                    break;
                case 2:
                    displayBranchAssets(branches, scanner);
                    break;
                case 3:
                    displayWarehouseAssets(branches, scanner);
                    break;
                case 4:
                    transactionAssets(branches, scanner);
                    break;
                case 5:
                    displayAllAssetMovement(branches);
                    break;
                case 6:
                    displayAssetMovement(branches, scanner);
                    break;
                case 0:
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }

        }
        scanner.close();
    }
}