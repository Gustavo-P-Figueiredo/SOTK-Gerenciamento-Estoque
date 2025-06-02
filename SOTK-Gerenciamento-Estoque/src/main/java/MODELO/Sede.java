package MODELO;

public class Sede {

    static int Sede_Id; //Get Setter
    String Sede_Nome; //Get Setter
    String Sede_Lider; //Get Setter
    String Sede_Cidade; //Get Setter
    String Sede_Rua; //Get Setter
    int Sede_Numeracao; //Get Setter

        public static int getSede_Id() {
            return Sede_Id;
        }

        public void setSede_Id(int sede_Id){
            this.Sede_Id = sede_Id;

        }

        public String getSede_Nome() {
            return Sede_Nome;
        }

        public String setSede_Nome(String Sede_Nome){
            this.Sede_Nome = Sede_Nome;
            return Sede_Nome;
        }

        public String getSede_Lider(){
            return Sede_Lider;
        }

        public String setSede_Lider(String Sede_Lider) {
            this.Sede_Lider = Sede_Lider;
            return Sede_Lider;
        }

        public String getSede_Cidade(){
            return Sede_Cidade;
        }

        public String setSede_Cidade(String Sede_Cidade){
            this.Sede_Cidade = Sede_Cidade;
            return Sede_Cidade;
        }

        public String getSede_Rua(){
            return Sede_Rua;
        }

        public String setSede_Rua(String Sede_Rua){
            this.Sede_Rua = Sede_Rua;
            return Sede_Rua;
        }

        public int getSede_Numeracao() {
            return Sede_Numeracao;
        }

        public int setSede_Numeracao(int Sede_Numeracao){
            this.Sede_Numeracao = Sede_Numeracao;
            return Sede_Numeracao;
        }

}




