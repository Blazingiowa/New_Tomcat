package eclipses;

import java.io.Serializable;

public class EclipsesBean implements Serializable{
private int EclipseNo;
private String varsion;
private int osusume;
private String gazo;

public EclipsesBean(int EclipseNo,String varsion,int osusume,String gazo)
{
	super();
	this.EclipseNo=EclipseNo;
	this.varsion=varsion;
	this.osusume=osusume;
	this.gazo=gazo;
}

public EclipsesBean()
{

}

public int getEclipseNo() {
	return EclipseNo;
}
public void setEclipseNo(int eclipseNo) {
	EclipseNo = eclipseNo;
}
public String getVarsion() {
	return varsion;
}
public void setVarsion(String varsion) {
	this.varsion = varsion;
}
public int getOsusume() {
	return osusume;
}
public void setOsusume(int osusume) {
	this.osusume = osusume;
}
public String getGazo() {
	return gazo;
}
public void setGazo(String gazo) {
	this.gazo = gazo;
}
}
