public class LuongTimNhoNhat extends Thread {
    private int maLuong;
    private int[] mangDuLieu;
    private int viTriBatDau;
    private int viTriKetThuc;
    private KetQuaLuong ketQua;
    
    public LuongTimNhoNhat(int maLuong, int[] mangDuLieu, int viTriBatDau, int viTriKetThuc) {
        this.maLuong = maLuong;
        this.mangDuLieu = mangDuLieu;
        this.viTriBatDau = viTriBatDau;
        this.viTriKetThuc = viTriKetThuc;
        this.ketQua = new KetQuaLuong();
    }
    
    @Override
    public void run() {
        long thoiDiemBatDau = System.currentTimeMillis();
        int giaTriNhoNhat = mangDuLieu[viTriBatDau];
        
        for (int i = viTriBatDau + 1; i < viTriKetThuc; i++) {
            if (mangDuLieu[i] < giaTriNhoNhat) {
                giaTriNhoNhat = mangDuLieu[i];
            }
            
            // In kết quả trung gian
            System.out.printf("Luồng T%d: %d - %dms%n", 
                    maLuong, giaTriNhoNhat, System.currentTimeMillis() - thoiDiemBatDau);
        }
        
        // Lưu kết quả
        ketQua.giaTriNhoNhat = giaTriNhoNhat;
        ketQua.thoiGianThucThi = System.currentTimeMillis() - thoiDiemBatDau;
    }
    
    public KetQuaLuong layKetQua() {
        return ketQua;
    }
}