package com.fast.model;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

import java.security.PrivateKey;
import java.security.cert.Certificate;

public class SignatureInfo {
    private  String reason; //签名的原因，显示在pdf签名属性中
    private  String location;//签名的地点，显示在pdf签名属性中
    private String digestAlgorithm;//摘要算法名称，例如SHA-1
    private String imagePath;//图章路径
    private  String fieldName;//表单域名称
    private  Certificate[] chain;//证书链
    private  PrivateKey pk;//签名私钥
    private  int certificationLevel = 0; //批准签章
    private PdfSignatureAppearance.RenderingMode renderingMode;//表现形式：仅描述，仅图片，图片和描述，签章者和描述
    //图章属性
    private float rectllx ;//图章左下角x
    private float rectlly ;//图章左下角y
    private float recturx ;//图章右上角x
    private float rectury ;//图章右上角y

    public float getRectllx() {
        return rectllx;
    }
    public void setRectllx(float rectllx) {
        this.rectllx = rectllx;
    }
    public float getRectlly() {
        return rectlly;
    }
    public void setRectlly(float rectlly) {
        this.rectlly = rectlly;
    }
    public float getRecturx() {
        return recturx;
    }
    public void setRecturx(float recturx) {
        this.recturx = recturx;
    }
    public float getRectury() {
        return rectury;
    }
    public void setRectury(float rectury) {
        this.rectury = rectury;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }
    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public Certificate[] getChain() {
        return chain;
    }
    public void setChain(Certificate[] chain) {
        this.chain = chain;
    }
    public PrivateKey getPk() {
        return pk;
    }
    public void setPk(PrivateKey pk) {
        this.pk = pk;
    }
    public int getCertificationLevel() {
        return certificationLevel;
    }
    public void setCertificationLevel(int certificationLevel) {
        this.certificationLevel = certificationLevel;
    }
    public PdfSignatureAppearance.RenderingMode getRenderingMode() {
        return renderingMode;
    }
    public void setRenderingMode(PdfSignatureAppearance.RenderingMode renderingMode) {
        this.renderingMode = renderingMode;
    }
}
