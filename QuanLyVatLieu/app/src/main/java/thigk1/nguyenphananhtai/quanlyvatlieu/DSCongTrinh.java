package thigk1.nguyenphananhtai.quanlyvatlieu;

public class DSCongTrinh {
    private String tenCongTrinh;
    private String ngayKhoiCong;
    private String diaDiem;
    private String donViThiCong;
    private String chuDauTu;
    private String moTa;
    private String quyMo;
    private String loiIch;

    // Constructor ngắn (chỉ có tên và ngày khởi công)
    public DSCongTrinh(String tenCongTrinh, String ngayKhoiCong) {
        this.tenCongTrinh = tenCongTrinh;
        this.ngayKhoiCong = ngayKhoiCong;
        this.diaDiem = "";
        this.donViThiCong = "";
        this.chuDauTu = "";
        this.moTa = "";
        this.quyMo = "";
        this.loiIch = "";
    }

    // Constructor đầy đủ thông tin
    public DSCongTrinh(String tenCongTrinh, String ngayKhoiCong, String diaDiem, String donViThiCong,
                       String chuDauTu, String moTa, String quyMo, String loiIch) {
        this.tenCongTrinh = tenCongTrinh;
        this.ngayKhoiCong = ngayKhoiCong;
        this.diaDiem = diaDiem;
        this.donViThiCong = donViThiCong;
        this.chuDauTu = chuDauTu;
        this.moTa = moTa;
        this.quyMo = quyMo;
        this.loiIch = loiIch;
    }

    public String getTenCongTrinh() {
        return tenCongTrinh;
    }

    public void setTenCongTrinh(String tenCongTrinh) {
        this.tenCongTrinh = tenCongTrinh;
    }

    public String getNgayKhoiCong() {
        return ngayKhoiCong;
    }

    public void setNgayKhoiCong(String ngayKhoiCong) {
        this.ngayKhoiCong = ngayKhoiCong;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getDonViThiCong() {
        return donViThiCong;
    }

    public void setDonViThiCong(String donViThiCong) {
        this.donViThiCong = donViThiCong;
    }

    public String getChuDauTu() {
        return chuDauTu;
    }

    public void setChuDauTu(String chuDauTu) {
        this.chuDauTu = chuDauTu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getQuyMo() {
        return quyMo;
    }

    public void setQuyMo(String quyMo) {
        this.quyMo = quyMo;
    }

    public String getLoiIch() {
        return loiIch;
    }

    public void setLoiIch(String loiIch) {
        this.loiIch = loiIch;
    }
}
