package datastructure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.lang.reflect.Method;

// IMPORTANT: 100% AI Generated

class WeightedQuickUnionTest {

    // ---------- isConnected ----------

    @Test
    @DisplayName("Ban đầu mọi phần tử chỉ kết nối với chính nó")
    void initiallyEachElementConnectedOnlyToItself() {
        WeightedQuickUnion ds = new WeightedQuickUnion(5);
        for (int i = 0; i < 5; i++) {
            assertTrue(ds.isConnected(i, i), "phần tử phải kết nối với chính nó");
        }
        assertFalse(ds.isConnected(0, 1));
        assertFalse(ds.isConnected(2, 4));
    }

    @Test
    @DisplayName("Sau connect, hai phần tử kết nối với nhau")
    void connectMakesTwoElementsConnected() {
        WeightedQuickUnion ds = new WeightedQuickUnion(5);
        ds.connect(0, 1);
        assertTrue(ds.isConnected(0, 1));
        assertTrue(ds.isConnected(1, 0));   // đối xứng
    }

    @Test
    @DisplayName("Tính bắc cầu: a~b, b~c => a~c")
    void connectivityIsTransitive() {
        WeightedQuickUnion ds = new WeightedQuickUnion(5);
        ds.connect(0, 1);
        ds.connect(1, 2);
        assertTrue(ds.isConnected(0, 2));
        assertTrue(ds.isConnected(2, 0));
    }

    @Test
    @DisplayName("connect trùng lặp không làm hỏng gì")
    void duplicateConnectIsIdempotent() {
        WeightedQuickUnion ds = new WeightedQuickUnion(3);
        ds.connect(0, 1);
        ds.connect(0, 1);
        ds.connect(1, 0);
        assertTrue(ds.isConnected(0, 1));
        assertFalse(ds.isConnected(0, 2));
    }

    @Test
    @DisplayName("Hai nhóm riêng biệt không kết nối với nhau")
    void separateGroupsStaySeparate() {
        WeightedQuickUnion ds = new WeightedQuickUnion(6);
        ds.connect(0, 1);
        ds.connect(1, 2);   // nhóm {0,1,2}
        ds.connect(3, 4);   // nhóm {3,4}
        assertTrue(ds.isConnected(0, 2));
        assertTrue(ds.isConnected(3, 4));
        assertFalse(ds.isConnected(0, 3));
        assertFalse(ds.isConnected(2, 5));
    }

    // ---------- connect ----------

    @Test
    @DisplayName("connect hai nhóm lớn gộp đúng")
    void connectTwoLargeGroups() {
        WeightedQuickUnion ds = new WeightedQuickUnion(10);
        // nhóm A: 0-1-2-3
        ds.connect(0, 1); ds.connect(1, 2); ds.connect(2, 3);
        // nhóm B: 4-5-6
        ds.connect(4, 5); ds.connect(5, 6);

        assertFalse(ds.isConnected(3, 4));
        ds.connect(3, 4);
        // giờ tất cả phải kết nối
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                assertTrue(ds.isConnected(i, j),
                        i + " và " + j + " phải kết nối sau khi gộp nhóm");
            }
        }
        assertFalse(ds.isConnected(0, 7));   // 7 vẫn tách biệt
    }

    @Test
    @DisplayName("connect(x, x) không làm hỏng cấu trúc")
    void selfConnectIsNoOp() {
        WeightedQuickUnion ds = new WeightedQuickUnion(3);
        ds.connect(1, 1);
        assertTrue(ds.isConnected(1, 1));
        assertFalse(ds.isConnected(1, 0));
        assertFalse(ds.isConnected(1, 2));
    }

    // ---------- findRoot (private, test qua reflection) ----------

    @Test
    @DisplayName("findRoot là private và trả về cùng root cho các phần tử cùng nhóm")
    void findRootReturnsSameRootForConnectedElements() throws Exception {
        WeightedQuickUnion ds = new WeightedQuickUnion(5);
        ds.connect(0, 1);
        ds.connect(1, 2);

        Method findRoot = WeightedQuickUnion.class.getDeclaredMethod("findRoot", int.class);
        findRoot.setAccessible(true);   // bypass private

        int r0 = (int) findRoot.invoke(ds, 0);
        int r1 = (int) findRoot.invoke(ds, 1);
        int r2 = (int) findRoot.invoke(ds, 2);

        assertEquals(r0, r1);
        assertEquals(r1, r2);
    }

    @Test
    @DisplayName("findRoot trả về chính nó khi là root")
    void findRootOfRootIsItself() throws Exception {
        WeightedQuickUnion ds = new WeightedQuickUnion(3);
        Method findRoot = WeightedQuickUnion.class.getDeclaredMethod("findRoot", int.class);
        findRoot.setAccessible(true);

        // chưa connect gì -> mọi phần tử là root của chính nó
        for (int i = 0; i < 3; i++) {
            assertEquals(i, (int) findRoot.invoke(ds, i));
        }
    }

    // ---------- path compression ----------

    @Test
    @DisplayName("Path compression: sau findRoot, các node trỏ thẳng về root")
    void pathCompressionFlattensTree() throws Exception {
        WeightedQuickUnion ds = new WeightedQuickUnion(6);
        // Xây chuỗi dài: 0-1-2-3-4-5 (tùy implementation có thể thành chuỗi)
        ds.connect(0, 1);
        ds.connect(1, 2);
        ds.connect(2, 3);
        ds.connect(3, 4);
        ds.connect(4, 5);

        Method findRoot = WeightedQuickUnion.class.getDeclaredMethod("findRoot", int.class);
        findRoot.setAccessible(true);

        int root = (int) findRoot.invoke(ds, 5);   // gọi findRoot trên node sâu nhất

        // Sau path compression, findRoot lại các node phải trả cùng root
        // và (nếu implementation chuẩn) parent của chúng trỏ thẳng về root.
        // Kiểm tra gián tiếp: mọi node đều connected và findRoot cho cùng kết quả.
        for (int i = 0; i <= 5; i++) {
            assertEquals(root, (int) findRoot.invoke(ds, i),
                    "sau path compression, findRoot(" + i + ") phải bằng root");
        }
    }

    @Test
    @DisplayName("Path compression: kiểm tra trực tiếp parent trỏ về root (nếu truy cập được)")
    void pathCompressionParentPointsToRoot() throws Exception {
        WeightedQuickUnion ds = new WeightedQuickUnion(5);
        ds.connect(0, 1);
        ds.connect(1, 2);
        ds.connect(2, 3);
        ds.connect(3, 4);

        Method findRoot = WeightedQuickUnion.class.getDeclaredMethod("findRoot", int.class);
        findRoot.setAccessible(true);
        int root = (int) findRoot.invoke(ds, 4);

        // Truy cập field parent (giả định tên là "parent" hoặc "p")
        // Điều chỉnh tên field theo code của bạn.
        java.lang.reflect.Field parentField = WeightedQuickUnion.class.getDeclaredField("parent");
        parentField.setAccessible(true);
        int[] parent = (int[]) parentField.get(ds);

        // Sau khi gọi findRoot(4), node 4 (và các node trên đường đi) phải trỏ thẳng về root
        assertEquals(root, parent[4],
                "path compression phải làm parent[4] trỏ thẳng về root");
    }

    // ---------- stress / tính đúng đắn tổng quát ----------

    @Test
    @DisplayName("Mô phỏng ngẫu nhiên: isConnected khớp với tập hợp gộp")
    void randomOperationsMatchSetUnion() {
        int n = 50;
        WeightedQuickUnion ds = new WeightedQuickUnion(n);
        // Dùng mảng đánh dấu nhóm bằng BFS đơn giản để đối chiếu
        boolean[][] expected = new boolean[n][n];
        for (int i = 0; i < n; i++) expected[i][i] = true;

        java.util.Random rnd = new java.util.Random(42);
        for (int step = 0; step < 200; step++) {
            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);
            ds.connect(a, b);
            // cập nhật expected: gộp nhóm chứa a và b
            for (int i = 0; i < n; i++) {
                if (expected[i][a]) {
                    for (int j = 0; j < n; j++) {
                        if (expected[j][b]) {
                            expected[i][j] = true;
                            expected[j][i] = true;
                        }
                    }
                }
            }
            // kiểm tra một vài cặp
            for (int t = 0; t < 5; t++) {
                int x = rnd.nextInt(n);
                int y = rnd.nextInt(n);
                assertEquals(expected[x][y], ds.isConnected(x, y),
                        "sai tại bước " + step + " với (" + x + "," + y + ")");
            }
        }
    }

    // ---------- edge cases ----------

    @Test
    @DisplayName("WeightedQuickUnion(1) hoạt động đúng")
    void singleElement() {
        WeightedQuickUnion ds = new WeightedQuickUnion(1);
        assertTrue(ds.isConnected(0, 0));
    }

    @Test
    @DisplayName("WeightedQuickUnion(0) không ném exception khi không thao tác")
    void emptySet() {
        assertDoesNotThrow(() -> new WeightedQuickUnion(0));
    }

    @Test
    @DisplayName("connect/isConnected với chỉ số ngoài biên ném exception (nếu bạn thiết kế vậy)")
    void outOfBoundsThrows() {
        WeightedQuickUnion ds = new WeightedQuickUnion(3);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> ds.isConnected(0, 5));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> ds.connect(5, 0));
    }
}