import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Nhập số phần tử và số luồng từ bàn phím
        System.out.print("Nhập số phần tử trong mảng (N > 100): ");
        int SO_PHAN_TU = scanner.nextInt();
        
        System.out.print("Nhập số luồng muốn tạo (k > 1): ");
        int SO_LUONG = scanner.nextInt();
        
        // Kiểm tra điều kiện đầu vào
        if (SO_PHAN_TU <= 100 || SO_LUONG <= 1) {
            System.out.println("Dữ liệu nhập không hợp lệ!");
            System.out.println("Yêu cầu: N > 100 và k > 1");
            scanner.close();
            return;
        }
        
        // 2. Tạo mảng ngẫu nhiên
        int[] mangDuLieu = new int[SO_PHAN_TU];
        Random boNgauNhien = new Random();
        
        System.out.print("Nhập giới hạn giá trị ngẫu nhiên (ví dụ 10000): ");
        int gioiHan = scanner.nextInt();
        
        for (int i = 0; i < SO_PHAN_TU; i++) {
            mangDuLieu[i] = boNgauNhien.nextInt(gioiHan);
        }
        
        // 3. Hiển thị 10 phần tử đầu để kiểm tra
        System.out.println("\n10 phần tử đầu trong mảng:");
        for (int i = 0; i < 10 && i < SO_PHAN_TU; i++) {
            System.out.print(mangDuLieu[i] + " ");
        }
        System.out.println("\n");
        
        // 4. Tạo và chạy các luồng
        LuongTimNhoNhat[] cacLuong = new LuongTimNhoNhat[SO_LUONG];
        KetQuaLuong[] ketQua = new KetQuaLuong[SO_LUONG];
        
        int kichThuocDoan = SO_PHAN_TU / SO_LUONG;
        
        for (int i = 0; i < SO_LUONG; i++) {
            int batDau = i * kichThuocDoan;
            int ketThuc = (i == SO_LUONG - 1) ? SO_PHAN_TU : (i + 1) * kichThuocDoan;
            
            cacLuong[i] = new LuongTimNhoNhat(i, mangDuLieu, batDau, ketThuc);
            cacLuong[i].start();
        }
        
        // 5. Chờ các luồng hoàn thành
        for (int i = 0; i < SO_LUONG; i++) {
            try {
                cacLuong[i].join();
                ketQua[i] = cacLuong[i].layKetQua();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // 6. Tổng hợp kết quả
        int giaTriNhoNhat = ketQua[0].giaTriNhoNhat;
        for (int i = 1; i < SO_LUONG; i++) {
            if (ketQua[i].giaTriNhoNhat < giaTriNhoNhat) {
                giaTriNhoNhat = ketQua[i].giaTriNhoNhat;
            }
        }
        
        System.out.println("\nPhần tử nhỏ nhất trong mảng là: " + giaTriNhoNhat);
        
        // In thời gian thực thi của từng luồng
        for (int i = 0; i < SO_LUONG; i++) {
            System.out.printf("Luồng T%d: %dms - Giá trị nhỏ nhất tìm được: %d%n", 
                           i, ketQua[i].thoiGianThucThi, ketQua[i].giaTriNhoNhat);
        }
        
        scanner.close();
    }
}