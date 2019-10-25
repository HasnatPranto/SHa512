import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class sha512 {

    public static void main(String[]args) throws IOException {

        String msg;
        byte[] bits;

        System.out.println("=*=*=*=*=*=*SHA-512=*=*=*=*=*=*\n");

        msg= new String(Files.readAllBytes(Paths.get("message.txt")));

       msg=msg.replaceAll("\r\n","\n");

       bits=msg.getBytes();

       bits=setPad(bits);

       System.out.println("<--512-bit hash value for the given text message-->\n"+generate_hash(bits));
    }

    public static byte[] setPad(byte[] bits){
        int len;
        len= 17 + bits.length;

        while(len%128!=0)
            len++;
        byte[] b = new byte[len];

        for(int i=0;i<bits.length;i++)
            b[i]=bits[i];
        b[bits.length]=(byte) 128;
        byte[] blen=BigInteger.valueOf(bits.length*8).toByteArray();

        for(int i=b.length-blen.length,j=0;j<blen.length;i++,j++)
            b[i]=blen[j];
        return b;
    }

    public static String generate_hash(byte[] bits){

        long[][] words=new long[bits.length/128][16];

        for(int i=0;i<bits.length/128;i++){

            for(int j=0;j<16;j++)
            {
                int ka,al;
                ka=j*8;
                al=ka+8;

                for(int k=ka; k<al; k++)
                    words[i][j]= (words[i][j]<<8) + (bits[(i*128)+k] & 255);

            }
        }

        ShaCore sCore = new ShaCore();

        sCore.set80words(words);

        return sCore.shaf(words.length);
    }
}
