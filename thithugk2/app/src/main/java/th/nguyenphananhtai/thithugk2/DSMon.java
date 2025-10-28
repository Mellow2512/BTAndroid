package th.nguyenphananhtai.thithugk2;

public class DSMon {
    private String tenMon;
    private String maMon;
    private int soTinChi;
    private String giangVien;
    private String moTa;
    private String noiDung;

    // Constructor cũ (giữ lại để không lỗi code cũ)
    public DSMon(String tenMon) {
        this.tenMon = tenMon;
        this.maMon = "";
        this.soTinChi = 3;
        this.giangVien = "";
        this.moTa = "";
        this.noiDung = "";
    }

    // Constructor mới với đầy đủ thông tin
    public DSMon(String tenMon, String maMon, int soTinChi, String giangVien, String moTa, String noiDung) {
        this.tenMon = tenMon;
        this.maMon = maMon;
        this.soTinChi = soTinChi;
        this.giangVien = giangVien;
        this.moTa = moTa;
        this.noiDung = noiDung;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public String getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(String giangVien) {
        this.giangVien = giangVien;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}