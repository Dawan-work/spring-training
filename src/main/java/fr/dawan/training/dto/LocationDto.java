package fr.dawan.training.dto;

public class LocationDto {
	private int id;
    private String slug;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String zipCode;
    private String city;
    private String country;
    private String furtherInfo;
    private String mapUrl;
    private String pictureFile;
    private boolean office;
    private boolean isPmi;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFurtherInfo() {
		return furtherInfo;
	}
	public void setFurtherInfo(String furtherInfo) {
		this.furtherInfo = furtherInfo;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public String getPictureFile() {
		return pictureFile;
	}
	public void setPictureFile(String pictureFile) {
		this.pictureFile = pictureFile;
	}
	public boolean isOffice() {
		return office;
	}
	public void setOffice(boolean office) {
		this.office = office;
	}
	public boolean isPmi() {
		return isPmi;
	}
	public void setPmi(boolean isPmi) {
		this.isPmi = isPmi;
	}
    
    
}
