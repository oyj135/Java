import resandlogin.Login;

public class Start {
    public static void main(String[] args) {
        //更加美观的窗口模式
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
            System.out.println(e);
        }
        new Login().setVisible(true);
    }
}
