package Modelos;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ModeloPlanCuentas {
    private static ModeloPlanCuentas variablesPlanCuentas;
    private String nivelPrefijo, prefijo, longPrefijo;
    private Boolean lAgregar;
    
    private JLabel codigoCta;
    private JTextField tfNombre;
    private JCheckBox chkActivo;
    private JComboBox cbCtaPadre, cbCtaNivelCrea, cbNivelPrev1, cbNivelPrev2, cbNivelPrev3, cbNivelPrev4, cbNivelPrev5, cbNivelPrev6, cbNivelPrev7;

    private ModeloPlanCuentas() {
    }
    
    public static ModeloPlanCuentas getDatosPlanCuentas(){
        if (variablesPlanCuentas == null){
            variablesPlanCuentas = new ModeloPlanCuentas();
        }

        return variablesPlanCuentas;
    }

    public Boolean getlAgregar() {
        return lAgregar;
    }

    public void setlAgregar(Boolean lAgregar) {
        this.lAgregar = lAgregar;
    }

    public String getNivelPrefijo() {
        return nivelPrefijo;
    }

    public void setNivelPrefijo(String nivelPrefijo) {
        this.nivelPrefijo = nivelPrefijo;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getLongPrefijo() {
        return longPrefijo;
    }

    public void setLongPrefijo(String longPrefijo) {
        this.longPrefijo = longPrefijo;
    }

    public JCheckBox getChkActivo() {
        return chkActivo;
    }

    public void setChkActivo(JCheckBox chkActivo) {
        this.chkActivo = chkActivo;
    }

    public JComboBox getCbCtaPadre() {
        return cbCtaPadre;
    }

    public void setCbCtaPadre(JComboBox cbCtaPadre) {
        this.cbCtaPadre = cbCtaPadre;
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JComboBox getCbCtaNivelCrea() {
        return cbCtaNivelCrea;
    }

    public void setCbCtaNivelCrea(JComboBox cbCtaNivelCrea) {
        this.cbCtaNivelCrea = cbCtaNivelCrea;
    }

    public JComboBox getCbNivelPrev1() {
        return cbNivelPrev1;
    }

    public void setCbNivelPrev1(JComboBox cbNivelPrev1) {
        this.cbNivelPrev1 = cbNivelPrev1;
    }

    public JComboBox getCbNivelPrev2() {
        return cbNivelPrev2;
    }

    public void setCbNivelPrev2(JComboBox cbNivelPrev2) {
        this.cbNivelPrev2 = cbNivelPrev2;
    }

    public JComboBox getCbNivelPrev3() {
        return cbNivelPrev3;
    }

    public void setCbNivelPrev3(JComboBox cbNivelPrev3) {
        this.cbNivelPrev3 = cbNivelPrev3;
    }

    public JComboBox getCbNivelPrev4() {
        return cbNivelPrev4;
    }

    public void setCbNivelPrev4(JComboBox cbNivelPrev4) {
        this.cbNivelPrev4 = cbNivelPrev4;
    }

    public JComboBox getCbNivelPrev5() {
        return cbNivelPrev5;
    }

    public void setCbNivelPrev5(JComboBox cbNivelPrev5) {
        this.cbNivelPrev5 = cbNivelPrev5;
    }

    public JComboBox getCbNivelPrev6() {
        return cbNivelPrev6;
    }

    public void setCbNivelPrev6(JComboBox cbNivelPrev6) {
        this.cbNivelPrev6 = cbNivelPrev6;
    }

    public JComboBox getCbNivelPrev7() {
        return cbNivelPrev7;
    }

    public void setCbNivelPrev7(JComboBox cbNivelPrev7) {
        this.cbNivelPrev7 = cbNivelPrev7;
    }

    public JLabel getCodigoCta() {
        return codigoCta;
    }

    public void setCodigoCta(JLabel codigoCta) {
        this.codigoCta = codigoCta;
    }
    
}
