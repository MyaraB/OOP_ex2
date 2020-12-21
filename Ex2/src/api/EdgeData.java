package api;

public class EdgeData implements  edge_data {
    private int src, dest, tag;
    private double weight;
    private String info;
    public EdgeData(int s, int d, double w){
        info = "";
        weight = w;
        src = s;
        dest = d;
    }
    @Override
    public int getSrc() {
        try {
            return src;
        } catch (Exception e){
            return -1;
        }
    }

    @Override
    public int getDest() {
        try {
            return dest;
        } catch (Exception e){
            return -1;
        }
    }

    @Override
    public double getWeight() {
        try {
            return weight;
        } catch (Exception e){
            return -1;
        }    }

    @Override
    public String getInfo() {
        try {
            return info;
        } catch (Exception e){
            return null;
        }    }

    @Override
    public void setInfo(String s) {
        info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        tag = t;
    }
}
