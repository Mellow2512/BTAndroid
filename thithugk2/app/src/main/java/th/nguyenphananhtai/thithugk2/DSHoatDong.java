package th.nguyenphananhtai.thithugk2;

public class DSHoatDong {
    private String tenHoatDong;
    private String thoiGian;
    private String diaDiem;
    private String donVi;
    private String doiTuong;
    private String moTa;
    private String noiDung;
    private String loiIch;

    // Constructor cũ (giữ lại để không lỗi code cũ)
    public DSHoatDong(String tenHoatDong, String thoiGian) {
        this.tenHoatDong = tenHoatDong;
        this.thoiGian = thoiGian;
        this.diaDiem = "";
        this.donVi = "";
        this.doiTuong = "";
        this.moTa = "";
        this.noiDung = "";
        this.loiIch = "";
    }

    // Constructor mới với đầy đủ thông tin
    public DSHoatDong(String tenHoatDong, String thoiGian, String diaDiem, String donVi,
                      String doiTuong, String moTa, String noiDung, String loiIch) {
        this.tenHoatDong = tenHoatDong;
        this.thoiGian = thoiGian;
        this.diaDiem = diaDiem;
        this.donVi = donVi;
        this.doiTuong = doiTuong;
        this.moTa = moTa;
        this.noiDung = noiDung;
        this.loiIch = loiIch;
    }

    public String getTenHoatDong() {
        return tenHoatDong;
    }

    public void setTenHoatDong(String tenHoatDong) {
        this.tenHoatDong = tenHoatDong;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public void setDoiTuong(String doiTuong) {
        this.doiTuong = doiTuong;
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

    public String getLoiIch() {
        return loiIch;
    }

    public void setLoiIch(String loiIch) {
        this.loiIch = loiIch;
    }
}