package MODELO;

public class Sede {
    int Sede_Id;
    String Sede_Nome;
    String Sede_Lider;
    int Sede_Numero;
    String Sede_Rua;
    String Sede_Cidade;

    public int getSede_Id() {
        return Sede_Id;
    }

    public static void setSede_Id(int sedeId) {
    }


    public String getSede_Nome() {
        return Sede_Nome;
    }

    public void setSede_Nome(String nome) {
        this.Sede_Nome = nome;
    }


    public String getSede_Lider() {
        return Sede_Lider;
    }

    public void setSede_Lider(String lider) {
        this.Sede_Lider = lider;
    }


    public int getSede_Numero() {
        return Sede_Numero;
    }

    public static void setSede_Numero(int sedeNumero) {
    }


    public String getSede_Rua() {
        return Sede_Rua;
    }

    public void setSede_Rua(String rua) {
        this.Sede_Rua = rua;
    }


    public String getSede_Cidade() {
        return Sede_Cidade;
    }

    public void setSede_Cidade(String cidade) {
        this.Sede_Cidade = cidade;
    }


}