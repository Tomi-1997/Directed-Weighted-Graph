package api;

import java.util.HashMap;

public class test {
    /** test class just for testing hashmap **/
    public static void main(String[]args)
    {
        HashMap<String , Vertex> h = new HashMap();
        h.put("0" , new Vertex( new Point3D(1,1,0) , 0));
//        System.out.println(h.size());
//        System.out.println(h.get("0").getInfo());
//        System.out.println(h.containsKey("1"));

        double phi = (1 + Math.sqrt(5)) / 2;
        System.out.println(phi);
        System.out.println(1/phi);
    }
}
