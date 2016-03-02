/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Jarda
 */
public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        mySomething();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
    //inicializuje všechny proměnné, vytvoří GUI
    private void mySomething() {
        okno = new JPanel();
        okno.setLayout(new BorderLayout());
        okno.setPreferredSize(new Dimension(600,400));
        panel=new JPanel(new GridLayout(16, 30, 0, 0));
        policko = new Tlacitko[16][];
        for(int i=0;i<16;i++)
        {
            policko[i] = new Tlacitko[30];
            for(int j=0;j<30;j++)
            {
                policko[i][j] = new Tlacitko();
                policko[i][j].i = i;
                policko[i][j].j = j;
                policko[i][j].mina = false;
                policko[i][j].found = false;
                policko[i][j].setText("");
                policko[i][j].setFont(policko[i][j].getFont().deriveFont(Font.BOLD));
                policko[i][j].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                policko[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                policko[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if(SwingUtilities.isLeftMouseButton(evt))
                            kliknuti(evt,null);
                        else
                            praveTlacitko(evt);
                    }
                });
                panel.add(policko[i][j]);
            }
        }
        
        spodek = new JPanel(new GridLayout(1,2,0,0));
        pocet = 99;
        pocetMin = new JLabel();
        spodek.add(pocetMin);
        cas = new JLabel();
        sekundy = 0;
        cas.setText("Čas: "+Integer.toString(sekundy));
        timer = new Timer();
        cas.setBorder(javax.swing.BorderFactory.createLineBorder(Color.black));
        pocetMin.setBorder(javax.swing.BorderFactory.createLineBorder(Color.black));
        lista = new JMenuBar();
        hra = new JMenu();
        hra.setText("Hra");
        nova_hra = new JMenuItem();
        nova_hra.setText("Nová hra");
        nova_hra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rozmisti();
            }
        });
        nova_hra.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        hra.add(nova_hra);
        statistika = new JMenuItem();
        statistika.setText("Statistika");
        statistika.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String data = "";
                try
                {
                    new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(),"records.txt").createNewFile();
                    InputStream inputStream = new FileInputStream(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(),"records.txt"));
                    Reader reader = new InputStreamReader(inputStream,"UTF-8");
                    BufferedReader in = new BufferedReader(reader);
                    int znak;
                    do
                    {
                        znak = in.read();
                        if(znak != -1)
                            data += (char) znak;
                    } while (znak != -1);
                    String[] rozdeleno =data.split("\n");
                }catch (IOException e)
                {
			e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, data, "Statistika", JOptionPane.PLAIN_MESSAGE);
            }
        });
        statistika.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        hra.add(statistika);
        lista.add(hra);
        spodek.add(cas);
        okno.add(panel,CENTER);
        okno.add(spodek,SOUTH);
        okno.add(lista,NORTH);
        add(okno);
        rozmisti();
        pack();
        setVisible(true);
    }     
    
    //deklarace proměnných
    private JPanel panel;
    private JPanel spodek;
    private JPanel okno;
    private JLabel pocetMin;
    private JLabel cas;
    private Tlacitko[][] policko;
    private Timer timer;
    private JMenuBar lista;
    private JMenu hra;
    private JMenuItem nova_hra;
    private JMenuItem statistika;
    
    int pocet;
    int sekundy;
   

    public class Tlacitko extends JLabel
    {
        public Tlacitko()
        {
            super();
        }
        private void setMina()
        {
            this.mina = true;
        }
        private boolean mina;
        private int i;
        private int j;
        private boolean found;
    }
    //rozmístí miny, zrestartuje čas a ukazatel min
    private void rozmisti()
    {
        int[] indexy = new int[16*30];
        int pocetIndexu = 16*30;
        sekundy = 0;
        pocet = 99;
        pocetMin.setText("Zbývá min: "+Integer.toString(pocet));
        for(int i=0;i<16;i++) 
            for(int j=0;j<30;j++)
            {
                policko[i][j].mina = false;
                policko[i][j].found = false;
                policko[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                policko[i][j].setText("");
                policko[i][j].setIcon(null);
            }
        
        for(int i = 0; i < 16*30; i++)
        {
            indexy[i] = i;
        }
        Random randomGenerator = new Random();
        int cislo;
        for(int i = 0; i<99; i++ )
        {
            cislo = randomGenerator.nextInt(pocetIndexu);
            pocetIndexu--;
            policko[indexy[cislo]/30][indexy[cislo]%30].setMina();
            indexy[cislo]=indexy[pocetIndexu];
        }
    }
    //obsluhuje kliknutí levého tlačítka myši
    //TODO: rozdělit do více kratších metod
    private boolean kliknuti(java.awt.event.MouseEvent evt,Tlacitko kliknutoo) {
        if(sekundy==0)
        {
            timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                sekundy++;
                cas.setText("Čas: "+Integer.toString(sekundy));
            }
            },0,1000);
        }
        Tlacitko kliknuto = new Tlacitko();
        if(kliknutoo == null)
            kliknuto = ((Tlacitko) evt.getSource());
        else
            kliknuto=kliknutoo;
        if(!kliknuto.found && kliknuto.getIcon() == null)
        {
            kliknuto.found = true;
            String[] vyber = {"Začít znovu","Ukončit"};
            //kliknutí na minu
            if(kliknuto.mina)
            {
                int choice = JOptionPane.showOptionDialog(null, //Component parentComponent
                               "Prohrál/a jste!", //Object message,
                               "Prohra!", //String title
                               JOptionPane.YES_NO_OPTION, //int optionType
                               JOptionPane.INFORMATION_MESSAGE, //int messageType
                               null, //Icon icon,
                               vyber, //Object[] options,
                               "Začít znovu");//Object initialValue 
                if(choice == 1)
                {
                    timer.cancel();
                    this.dispose();
                }
                else
                {
                    rozmisti();
                    return true;
                }
            }
            else
            {
                int pocetMin = 0;
                kliknuto.setBorder(javax.swing.BorderFactory.createLineBorder(Color.lightGray));
                if (kliknuto.i + 1 < 16 &&policko[kliknuto.i+1][kliknuto.j].mina)
                    pocetMin++;
                if (kliknuto.i - 1 >=0 && policko[kliknuto.i-1][kliknuto.j].mina)
                    pocetMin++;
                if (kliknuto.j + 1 < 30 && policko[kliknuto.i][kliknuto.j+1].mina)
                    pocetMin++;
                if (kliknuto.j - 1 >=0 && policko[kliknuto.i][kliknuto.j-1].mina)
                    pocetMin++;
                if (kliknuto.j + 1 < 30 && kliknuto.i + 1 < 16 &&policko[kliknuto.i+1][kliknuto.j+1].mina)
                    pocetMin++;
                if (kliknuto.j - 1 >=0 && kliknuto.i + 1 < 16 &&policko[kliknuto.i+1][kliknuto.j-1].mina)
                    pocetMin++;
                if (kliknuto.i - 1 >=0 && kliknuto.j + 1 < 30 && policko[kliknuto.i-1][kliknuto.j+1].mina)
                    pocetMin++;
                if (kliknuto.j - 1 >=0 && kliknuto.i - 1 >=0 && policko[kliknuto.i-1][kliknuto.j-1].mina)
                    pocetMin++;
                if(pocetMin>0)
                {
                    switch(pocetMin)
                    {
                        case 1:
                            kliknuto.setForeground(Color.blue);
                            break;
                        case 2:
                            kliknuto.setForeground(Color.green.darker());
                            break;
                        case 3:
                            kliknuto.setForeground(Color.red);
                            break;
                        case 4:
                            kliknuto.setForeground(Color.BLUE.darker().darker());
                            break;
                        case 5:
                            kliknuto.setForeground(Color.red.darker());
                            break;
                        case 6:
                            kliknuto.setForeground(Color.CYAN);
                            break;
                        case 7:
                            kliknuto.setForeground(Color.black);
                            break;
                        case 8:
                            kliknuto.setForeground(Color.gray);
                            break;
                    }
                    kliknuto.setText(Integer.toString(pocetMin));
                }
                else
                {
                    if(kliknuto.i > 0)
                    {
                        kliknuti(evt,policko[kliknuto.i-1][kliknuto.j]);
                        if(kliknuto.j>0)
                            kliknuti(evt,policko[kliknuto.i-1][kliknuto.j-1]);
                        if(kliknuto.j+1<30)
                            kliknuti(evt,policko[kliknuto.i-1][kliknuto.j+1]);
                    }
                    if(kliknuto.i+1 < 16)
                    {
                        kliknuti(evt,policko[kliknuto.i+1][kliknuto.j]);
                        if(kliknuto.j>0)
                            kliknuti(evt,policko[kliknuto.i+1][kliknuto.j-1]);
                        if(kliknuto.j+1<30)
                            kliknuti(evt,policko[kliknuto.i+1][kliknuto.j+1]);
                    }
                    if(kliknuto.j>0)
                        kliknuti(evt,policko[kliknuto.i][kliknuto.j-1]);
                    if(kliknuto.j+1<30)
                        kliknuti(evt,policko[kliknuto.i][kliknuto.j+1]);
                }
            }
            //kontrola výhry
            int pocetNalezenych = 0;
            for(int i = 0; i < 16; i++)
                for(int j = 0; j<30;j++)
                {
                    if(policko[i][j].found)
                        pocetNalezenych++;
                }
            if(pocetNalezenych ==16*30-99&&!kliknuto.mina) 
            {
                String data = "";
                String vysledek = "";
                try
                {
                    String cas;
                    if(sekundy>60)
                        cas = Integer.toString(sekundy/60)+" minut "+Integer.toString(sekundy%60)+" sekund";
                    else
                        cas = Integer.toString(sekundy)+" sekund";
                    new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(),"records.txt").createNewFile();
                    InputStream inputStream = new FileInputStream(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(),"records.txt"));
                    Reader reader = new InputStreamReader(inputStream,"UTF-8");
                    BufferedReader in = new BufferedReader(reader);
                    int znak;
                    do
                    {
                        znak = in.read();
                        if(znak != -1)
                            data += (char) znak;
                    } while (znak != -1);
                    String[] rozdeleno =data.split("\n");
                    int i =0;
                    for(; i<rozdeleno.length;i++)
                    {
                        if(!cas.contains("minut"))
                        {
                            if(rozdeleno[i].contains("minut"))
                            {
                                vysledek += "<html><b>"+cas+"</b></html>"+"\n";
                                break;
                            }
                            else
                            {
                                if(Integer.parseInt(cas.split(" ")[0])<Integer.parseInt(rozdeleno[i].split(" ")[0]))
                                {
                                    vysledek += "<html><b>"+cas+"</b></html>"+"\n";
                                    break;
                                }
                                else
                                    vysledek += rozdeleno[i]+"\n";
                            }
                        }
                        else
                        {
                            if(rozdeleno[i].contains("minut"))
                            {
                                if(Integer.parseInt(cas.split(" ")[0])<Integer.parseInt(rozdeleno[i].split(" ")[0]))
                                {
                                    vysledek += "<html><b>"+cas+"</b></html>"+"\n";
                                    break;
                                }
                                else if(Integer.parseInt(cas.split(" ")[0])==Integer.parseInt(rozdeleno[i].split(" ")[0])&&Integer.parseInt(cas.split(" ")[2])<Integer.parseInt(rozdeleno[i].split(" ")[2]))
                                {
                                    vysledek += "<html><b>"+cas+"</b></html>"+"\n";
                                    break;
                                }
                                else
                                    vysledek += rozdeleno[i]+"\n";
                            }
                            else
                                vysledek += rozdeleno[i]+"\n";
                        }
                    }
                    if(i==rozdeleno.length)
                        vysledek+="<html><b>"+cas+"</b></html>"+"\n";
                    else for(;i<rozdeleno.length;i++)
                        vysledek+=rozdeleno[i]+"\n";
                    //JOptionPane.showMessageDialog(this, "Vyhrál/a jste!\n\n"+vysledek, "Výhra", JOptionPane.PLAIN_MESSAGE);
                    int choice = JOptionPane.showOptionDialog(null, //Component parentComponent
                               "Vyhrál/a jste!\n\n"+vysledek, //Object message,
                               "Výhra!", //String title
                               JOptionPane.YES_NO_OPTION, //int optionType
                               JOptionPane.INFORMATION_MESSAGE, //int messageType
                               null, //Icon icon,
                               vyber, //Object[] options,
                               "Začít znovu");//Object initialValue 
                    
                    OutputStream outputStream = new FileOutputStream(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(),"records.txt"));
                    Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter out = new BufferedWriter(writer);
                    rozdeleno = vysledek.split("<html><b>");
                    vysledek =rozdeleno[0];
                    vysledek +=rozdeleno[1].split("</b></html>")[0];
                    vysledek +=rozdeleno[1].split("</b></html>")[1];
                    out.write(vysledek);
                    out.close();
                    if(choice == 1)
                    {
                        timer.cancel();
                        this.dispose();
                    }
                    else
                    {
                        rozmisti();
                        return true;
                    }
                }catch (IOException e)
                {
			e.printStackTrace();
                }
            }
        }
        //kliknutí na odkryté číslo
        else if(kliknuto.found && kliknuto.getText() != "" && kliknutoo==null)
        {
            int cislo = Integer.parseInt(kliknuto.getText());
            if(kliknuto.i<15&&policko[kliknuto.i+1][kliknuto.j].getIcon()!=null)
                cislo--;
            if(kliknuto.i<15&&kliknuto.j<29&&policko[kliknuto.i+1][kliknuto.j+1].getIcon()!=null)
                cislo--;
            if(kliknuto.i<15&&kliknuto.j>0&&policko[kliknuto.i+1][kliknuto.j-1].getIcon()!=null)
                cislo--;
            if(kliknuto.i>0&&policko[kliknuto.i-1][kliknuto.j].getIcon()!=null)
                cislo--;
            if(kliknuto.i>0&&kliknuto.j<29&&policko[kliknuto.i-1][kliknuto.j+1].getIcon()!=null)
                cislo--;
            if(kliknuto.i>0&&kliknuto.j>0&&policko[kliknuto.i-1][kliknuto.j-1].getIcon()!=null)
                cislo--;
            if(kliknuto.j<29&&policko[kliknuto.i][kliknuto.j+1].getIcon()!=null)
                cislo--;
            if(kliknuto.j>0&&policko[kliknuto.i][kliknuto.j-1].getIcon()!=null)
                cislo--;
            if(cislo==0)
            {
                boolean nHra = false;
                if(kliknuto.i<15&&policko[kliknuto.i+1][kliknuto.j].getIcon()==null&& !policko[kliknuto.i+1][kliknuto.j].found)
                    nHra = kliknuti(evt,policko[kliknuto.i+1][kliknuto.j]);
                if(kliknuto.i<15&&kliknuto.j<29&&policko[kliknuto.i+1][kliknuto.j+1].getIcon()==null&& !policko[kliknuto.i+1][kliknuto.j+1].found&& !nHra)
                    nHra = kliknuti(evt,policko[kliknuto.i+1][kliknuto.j+1]);
                if(kliknuto.i<15&&kliknuto.j>0&&policko[kliknuto.i+1][kliknuto.j-1].getIcon()==null&& !policko[kliknuto.i+1][kliknuto.j-1].found&& !nHra)
                    nHra = kliknuti(evt,policko[kliknuto.i+1][kliknuto.j-1]);
                if(kliknuto.i>0&&policko[kliknuto.i-1][kliknuto.j].getIcon()==null&& !policko[kliknuto.i-1][kliknuto.j].found&& !nHra)
                    nHra = kliknuti(evt,policko[kliknuto.i-1][kliknuto.j]);
                if(kliknuto.i>0&&kliknuto.j<29&&policko[kliknuto.i-1][kliknuto.j+1].getIcon()==null&& !policko[kliknuto.i-1][kliknuto.j+1].found&& !nHra)
                    nHra = kliknuti(evt,policko[kliknuto.i-1][kliknuto.j+1]);
                if(kliknuto.i>0&&kliknuto.j>0&&policko[kliknuto.i-1][kliknuto.j-1].getIcon()==null&& !policko[kliknuto.i-1][kliknuto.j-1].found&& !nHra)
                    nHra = kliknuti(evt,policko[kliknuto.i-1][kliknuto.j-1]);
                if(kliknuto.j<29&&policko[kliknuto.i][kliknuto.j+1].getIcon()==null&& !policko[kliknuto.i][kliknuto.j+1].found&& !nHra)
                    nHra = kliknuti(evt,policko[kliknuto.i][kliknuto.j+1]);
                if(kliknuto.j>0&&policko[kliknuto.i][kliknuto.j-1].getIcon()==null&& !policko[kliknuto.i][kliknuto.j-1].found&& !nHra)
                    nHra = kliknuti(evt,policko[kliknuto.i][kliknuto.j-1]);
            }
        }
        return false;
    }
    //kliknutí pravým tlačítkem
    private void praveTlacitko(java.awt.event.MouseEvent evt)
    {
        Tlacitko kliknuto = ((Tlacitko) evt.getSource());
        if(!kliknuto.found)
        {
            if(kliknuto.getIcon()==null)
            {
                kliknuto.setIcon(new ImageIcon("sprite100.png"));
                pocet--;
                pocetMin.setText("Zbývá min: "+Integer.toString(pocet));
            }
            else
            {
                kliknuto.setIcon(null);
                pocet++;
                pocetMin.setText("Zbývá min: "+Integer.toString(pocet));
            }
        }
    }
}
