package javagameapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * Kalıtım ile Game sınıfı içerisine JFrame özelliği verilmiştir.
 * @author ahmetgokhangurel
 * @version 1.0.0
 * @since  2017-09-15
 */
public class Game extends JFrame
{
    //Global Değişkenler
    public int rows = 8,cols = 8;
    public ArrayList<Cell> clicked = new ArrayList();
    public Cell lastPlayer = new Cell();
    Cell[][] map;
    JPanel boardpanel;
    
    /**
     * Kurucu Method
     */
    public Game()
    {
        setBoard();
        JOptionPane.showMessageDialog(rootPane, "Siyah Taş ile Başla!", "The Rock Game", 1);
    }
    
    /**
     * setBoard methodu -
     * Oyunun başlangıç tablosu oluşturulmuştur.
     * İlk olarak JFrame özellikleri tanımlanmıştır.
     * JMenuBar tanımlanmıştır.
     * JPanel içerisine eklenecek Button'ların özellikleri tanımlanmıştır. 
     * Button'lara ActionListener özelliği verilmiştir.
     * Oyuncuların başlangıç taşları tabloya eklenmiştir.
     */
    public void setBoard()
    {
        //Frame Özellikleri
        setTitle("The Rock Game"); 
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setSize(width/2, height/2);
        //setLocationRelativeTo(null);      
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);          
        JMenu menu1 = new JMenu("Yeni");
        menubar.add(menu1);
        JMenuItem yeni = new JMenuItem("Yeni Oyun");
        menu1.add(yeni);
        JMenuItem cikis = new JMenuItem("Çıkış");
        menu1.add(cikis);
        JMenu menu2 = new JMenu("Yardım");
        menubar.add(menu2);
        JMenuItem yardim = new JMenuItem("Oyunun Kuralları");
        menu2.add(yardim);
        
        yeni.addActionListener(new ActionListener() {                 
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        new Game().setVisible(true);
                        dispose();
                  }
            });       
        cikis.addActionListener(new ActionListener() {                 
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        System.exit(0);                      
                  }
            });
        yardim.addActionListener(new ActionListener() {                
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(rootPane, "1 - Bir adım attığınızda taş kendini kopyalar.\n"
                                                              + "2 - İki adım attığınızda taş kendini taşır.\n"
                                                              + "3 - Hareket ettiği yerin etrafında farklı taş varsa onları dönüştürür.\n"
                                                              + "4 - Oyunda bütün taşları kendi rengine dönüştüren kazanır. !!!"
                                , "The Rock Game", 1);                     
                  }
            });
                  
        //Board Panel
        boardpanel = new JPanel(new GridLayout(8,8));        
        map = new Cell[rows][cols];
		
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                map[i][j] = new Cell();
                map[i][j].setCor(i, j);
                map[i][j].setSize(50, 50);
                map[i][j].setBackground(Color.white);
                map[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                map[i][j].addActionListener(active);
                boardpanel.add(map[i][j]);
            }
        }
        
        //Taşları Başlangıç Yerine Koymak İçin
        map[0][0].setBlack();
        map[rows - 1][0].setWhite();
        map[0][cols - 1].setWhite();
        map[rows - 1][cols - 1].setBlack();
        lastPlayer.setWhite();    
        add(boardpanel, BorderLayout.CENTER);
    }
    
    //Herbir Butonu Aktif Hale Getirmek İçin
    ActionListener active = new ActionListener(){ 
    
    @Override
    public void actionPerformed(ActionEvent evt) 
    {
        Cell c = (Cell)evt.getSource();
        if (clicked.isEmpty() && c.isEmpty())//Tıklanan ve aktif olan button boşsa birşey yapma 
        {
            return;
        }
        if (isStartTurn(c))//Sırayı belirlemek için 
	{
            return;
        }
        if (clicked.isEmpty()) //İlk Tıklamada Yörünge Aç
	{
            clicked.add(c);
            showLegalMoves(c);
        }        
	else if (clicked.get(0).getCor() == c.getCor()) //İkinci Tıklamada Yörünge Kapat 
	{
            clicked.clear();
            notShowLegalMoves(c);
        } 
	else if (c.getBackground() == c.getLightgray()) //Bir Adım Kontrolü
	{
            notShowLegalMoves(clicked.get(0));
            if (clicked.get(0).isBlack()) 
            {
                c.setBlack();
            } 
            else 
            {
                c.setWhite();
            }
            setLastPlayer(c);
            clicked.clear();
            changeRockColor(c);
        } 
        else if (c.getBackground() == c.getCyan()) //İki Adım Kontrolü 
	{
            notShowLegalMoves(clicked.get(0));
            if (clicked.get(0).isBlack()) 
            {
                c.setBlack();
            } 
            else 
            {
                c.setWhite();
            }
            setLastPlayer(c);
            clicked.get(0).setEmpty();
            clicked.clear();
            changeRockColor(c);
        }
        if (isTheEnd()) 
	{
            JOptionPane.showMessageDialog(rootPane, "Oyun Bitti!", "The End", 1);
        }              
    }   
    };
    
    /**
     * isStartTurn methodu -
     * Oyunun hangi taşla başlayacağını belirlemek için tanımlanmıştır.
     * @param c parametresi ile fonksiyona tıklanan butonun nesne özellikleri alınmıştır.
     * @return true veya false döndürmüştür. Bu da oyuna hangi taşla başlanacağının belirlenmesi için yapılmıştır.
     */
    public boolean isStartTurn(Cell c) 
    {
        if (lastPlayer.isBlack() && c.isBlack()) 
	{
            return true;
        }
        if (lastPlayer.isWhite() && c.isWhite()) 
	{
            return true;
        }
        return false;
    }
    
    /**
     * showLegalMoves methodu -
     * Oyuncunun sırası geldiğinde, 
     * kendi taşına tıkladığında gidebileceği yerlerdeki button'ların arkaplan renkleri değiştirilmiştir.
     * Bir adım için gri, iki adım için olanlar turkuaz renge dönüştürülmüştür.
     * Yörünge açılmış ve görülmesi sağlanmıştır.
     * @param c parametresi ile nesneden fonksiyona koordinatlar getirilip renk değiştirilmesi yapılmıştır.
     */
    public void showLegalMoves(Cell c) 
    {
        int i,j;
        if (c.isEmpty()) 
        {
            return;
        }
        for (i = c.getCor()[0] - 2; i <= c.getCor()[0] + 2; ++i) 
        {
            for (j = c.getCor()[1] - 2; j <= c.getCor()[1] + 2; ++j) 
            {
                if (i <= -1 || j <= -1 || i >= rows || j >= cols || !map[i][j].isEmpty()) continue;
                map[i][j].setBackCyan();
            }
        }
        for (i = c.getCor()[0] - 1; i <= c.getCor()[0] + 1; ++i) 
        {
            for (j = c.getCor()[1] - 1; j <= c.getCor()[1] + 1; ++j) 
            {
                if (i <= -1 || j <= -1 || i >= rows || j >= cols || !map[i][j].isEmpty()) continue;
                map[i][j].setBackLightgray();
            }
        }
    }
    
    /**
     * notShowLegalMoves methodu -
     * Oyuncu yörüngesi açık olan taşına tekrar tıkladığında yörüngenin kapatılması sağlanmıştır. 
     * @param c paratmesi ile nesneden koordinatlar alınmıştır.
     */
    public void notShowLegalMoves(Cell c) 
    {
        if (c.isEmpty()) 
        {
            return;
        }
        for (int i = c.getCor()[0] - 2; i <= c.getCor()[0] + 2; ++i) {
            for (int j = c.getCor()[1] - 2; j <= c.getCor()[1] + 2; ++j) {
                if (i <= -1 || j <= -1 || i >= rows || j >= cols) continue;
                if ((i + j) % 2 != 0) 
                {
                    map[i][j].setBackWhite();
                    continue;
                }
                map[i][j].setBackWhite();              
            }
        }
    }
       
    /**
     * changeRockColor methodu -
     * Gidilen noktanın etrafındaki 8 kare içerisinde taşın renginin zıttı olan bir renk varsa 
     * o renk taşın kendi rengine dönüştürülmüştür.
     * @param c parametresi ile gidilen noktanın bir adım uzaklıktaki koordinatlar alınmıştır.
     */
    public void changeRockColor(Cell c) 
    {
        for (int i = c.getCor()[0] - 1; i <= c.getCor()[0] + 1; ++i) 
        {
            for (int j = c.getCor()[1] - 1; j <= c.getCor()[1] + 1; ++j) 
            {
                if (i <= -1 || j <= -1 || i >= rows || j >= cols) continue;
                if (c.isBlack() && this.map[i][j].isWhite()) 
                {
                    map[i][j].setBlack();
                    continue;
                }
                if (!c.isWhite() || !map[i][j].isBlack()) continue;
                map[i][j].setWhite();
            }
        }
    }
    
    /**
     * setLastPlayer methodu -
     * Son oyuncunun belirlenmesi sağlanmıştır.
     * İlk olarak son oyuncu beyaz taş tanımlanmıştır.
     * @param c parametresi ile son oyuncuya koordinat tanımlanmıştır.
     */
    public void setLastPlayer(Cell c) 
    {
        if (c.isBlack()) 
        {
            lastPlayer.setBlack();
        } 
        else if (c.isWhite()) 
        {
            lastPlayer.setWhite();
        }
        lastPlayer.setCor(c.getCor());
    }
    
    /**
     * isTheEnd methodu -
     * Siyah ve beyaz taşın sayısına bakarak oyunun bitip bitmediği kontrol edilmiştir.
     * @return Oyunun bitip bitmediğini kontrol etmek için tanımlanmıştır.
     */
    public boolean isTheEnd() 
    {
        int blackCnt = 0,whiteCnt = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (map[i][j].isBlack()) 
                {
                    ++blackCnt;
                    continue;
                }
                if (!map[i][j].isWhite()) continue;
                    ++whiteCnt;
            }
        }
        if (blackCnt == 64 || whiteCnt == 64) 
	{
            return true;
        }
        if (blackCnt == 0 || whiteCnt == 0) 
	{
            return true;
        }
        return false;
    }
    /**
     * Main methodu
     * @param args argümentler
     */
    public static void main(String args[])
    {
        new Game().setVisible(true);
    }
                
}
