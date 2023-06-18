package entity;

public class Commodity {
	
	private String imgUrl;
	private String name;
	private String about;
	private String price;
	
	public Commodity() {
		
	}
	
	
	public Commodity(String imgUrl, String name, String about, String price) {
		super();
		this.imgUrl = imgUrl;
		this.name = name;
		this.about = about;
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

}
