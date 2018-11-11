package javagameapp;

import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Kalıtım ile Cell sınıfı içerisine JButton özelliği verilmiştir.
 * @author ahmetgokhangurel
 * @version 1.0.0
 * @since  2017-09-15
 */
public class Cell extends JButton
{
    private boolean isBlack = false;
    private boolean isWhite = false;
    private boolean isEmpty = true;
    private Color lightgray = new Color(211, 211, 211);
    private Color cyan = new Color(0, 255, 255);
    private int[] cor;
    Image black,white,empty;
    /**
     * Kurucu Method -
     * Resimlerin button'lara yüklenmesi sağlanmıştır.
     * Koordinatlar için cor dizisi tanımlanmıştır.
     */
    public Cell()
    {
        try
        {
            this.black = ImageIO.read(getClass().getResource("images/dark.png"));
            this.white = ImageIO.read(getClass().getResource("images/light.png"));
            this.empty = null;          
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
        this.cor = new int[2];       
    }
    /**
     * setBlack methodu -
     * Hücreye Siyah Taş Özelliği Verilmek İçin Tanımlanmıştır.
     */
    public void setBlack() 
    {
        this.setIcon(new ImageIcon(this.black));
        this.isBlack = true;
        this.isWhite = false;
        this.isEmpty = false;
    }
    
    /**
     * setWhite methodu -
     * Hücreye Beyaz Taş Özelliği Verilmek İçin Tanımlanmıştır.
     */
    public void setWhite() 
    {
        this.setIcon(new ImageIcon(this.white));
        this.isWhite = true;
        this.isBlack = false;
        this.isEmpty = false;        
    }
    
    /**
     * setEmpty methodu -
     * Hücreye Boş Özelliği Verilmek İçin Tanımlanmıştır.
     */
    public void setEmpty() 
    {
        this.setIcon(new ImageIcon(""));
        this.isEmpty = true;
        this.isBlack = false;       
        this.isWhite = false;
    }
    
    /**
     * isBlack methodu -
     * Hücrede Siyah Taş Var Mı ?
     * @return false 
     */
    public boolean isBlack() 
    {
        return this.isBlack;
    }
    
    /**
     * isWhite methodu -
     * Hücrede Beyaz Taş Var Mı ?
     * @return false 
     */
    public boolean isWhite() 
    {
        return this.isWhite;
    }
    
    /**
     * isEmpty methodu -
     * Hücre Boş Mu ?
     * @return true 
     */
    public boolean isEmpty() 
    {
        return this.isEmpty;
    }
    
    /**
     * getLightgray methodu -
     * @return açık gri renk döndürür.
     */
    public Color getLightgray() 
    {
        return this.lightgray;
    }
    
    /**
     * getCyan methodu -
     * @return turkuaz renk döndürür.
     */
    public Color getCyan() 
    {
        return this.cyan;
    }
    
    /**
     * setBackWhite methodu -
     * Hücrenin arkaplan rengini beyaz yapmıştır.
     */
    public void setBackWhite() 
    {
        this.setBackground(Color.white);
    }
    
    /**
     * setBackLightgray methodu -
     * Hücrenin arkaplan rengini açık gri yapmıştır.
     */
    public void setBackLightgray() 
    {
        this.setBackground(this.lightgray);
    }
    
    /**
     * setBackCyan methodu -
     * Hücrenin arkaplan rengini turkuaz yapmıştır.
     */
    public void setBackCyan() 
    {
        this.setBackground(this.cyan);
    }
    
    /**
     * getCor methodu -
     * Hücrenin koordinatını getirmiştir.
     * @return koordinatı almıştır.
     */
    public int[] getCor()
    {
        return this.cor;
    }
    
    /**
     * setCor methodu -
     * cor dizisinin içindeki bilgileri almıştır.
     * @param cor hücrenin kordinat verileri ile değiştirmiştir.
     */
    public void setCor(int[] cor) 
    {
        this.cor = cor;
    }
    
    /**
     * setCor methodu -
     * @param row satır değeri
     * @param column sütun değeri
     * parametrelerini almıştır. cor dizisinin ilk elemanını row,
     * ikinci elemanını column yapmıştır.
     */
    public void setCor(int row, int column) 
    {
        this.cor[0] = row;
        this.cor[1] = column;
    }          
}
