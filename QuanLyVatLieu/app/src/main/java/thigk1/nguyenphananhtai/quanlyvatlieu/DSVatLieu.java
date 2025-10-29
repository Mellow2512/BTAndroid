package thigk1.nguyenphananhtai.quanlyvatlieu;

public class DSVatLieu {
    private String tenVatLieu;
    private String maVatLieu;
    private String loaiVatLieu;
    private String nhaCungCap;
    private String moTa;
    private String thongTinChiTiet;

    // Constructor ngắn (nếu chỉ cần tên)
    public DSVatLieu(String tenVatLieu) {
        this.tenVatLieu = tenVatLieu;
        this.maVatLieu = "";
        this.loaiVatLieu = "";
        this.nhaCungCap = "";
        this.moTa = "";
        this.thongTinChiTiet = "";
    }

    // Constructor đầy đủ
    public DSVatLieu(String tenVatLieu, String maVatLieu, String loaiVatLieu,
                     String nhaCungCap, String moTa, String thongTinChiTiet) {
        this.tenVatLieu = tenVatLieu;
        this.maVatLieu = maVatLieu;
        this.loaiVatLieu = loaiVatLieu;
        this.nhaCungCap = nhaCungCap;
        this.moTa = moTa;
        this.thongTinChiTiet = thongTinChiTiet;
    }

    public String getTenVatLieu() {
        return tenVatLieu;
    }

    public void setTenVatLieu(String tenVatLieu) {
        this.tenVatLieu = tenVatLieu;
    }

    public String getMaVatLieu() {
        return maVatLieu;
    }

    public void setMaVatLieu(String maVatLieu) {
        this.maVatLieu = maVatLieu;
    }

    public String getLoaiVatLieu() {
        return loaiVatLieu;
    }

    public void setLoaiVatLieu(String loaiVatLieu) {
        this.loaiVatLieu = loaiVatLieu;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getThongTinChiTiet() {
        return thongTinChiTiet;
    }

    public void setThongTinChiTiet(String thongTinChiTiet) {
        this.thongTinChiTiet = thongTinChiTiet;
    }
}
