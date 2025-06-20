package MODELO;

public class Fornecedor {
    public static String getFornecedor_Catalogo;
    int Fornecedor_Id;
    String Fornecedor_Nome;
    String Fornecedor_Cnpj;
    String Fornecedor_Catalogo;

    public int getFornecedor_Id() {
        return Fornecedor_Id;
    }

    public void setFornecedor_Id(int fornecedor_Id) {
        Fornecedor_Id = fornecedor_Id;
    }

    public String getFornecedor_Nome() {
        return Fornecedor_Nome;
    }

    public void setFornecedor_Nome(String fornecedor_Nome) {
        Fornecedor_Nome = fornecedor_Nome;
    }

    public String getFornecedor_Cnpj() {
        return Fornecedor_Cnpj;
    }

    public void setFornecedor_Cnpj(String fornecedor_Cnpj) {
        Fornecedor_Cnpj = fornecedor_Cnpj;
    }

    public String getFornecedor_Catalogo() {
        return Fornecedor_Catalogo;
    }

    public void setFornecedor_Catalogo(String fornecedor_Catalogo) {
        Fornecedor_Catalogo = fornecedor_Catalogo;
    }
}
