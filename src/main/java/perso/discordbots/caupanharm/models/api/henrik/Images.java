package perso.discordbots.caupanharm.models.api.henrik;

public class Images {
    String small, large, triangle_down, triangle_up;

    public Images() {

    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getTriangle_down() {
        return triangle_down;
    }

    public void setTriangle_down(String triangle_down) {
        this.triangle_down = triangle_down;
    }

    public String getTriangle_up() {
        return triangle_up;
    }

    public void setTriangle_up(String triangle_up) {
        this.triangle_up = triangle_up;
    }

    @Override
    public String toString() {
        return "Images{" +
                "small='" + small + '\'' +
                ", large='" + large + '\'' +
                ", triangle_down='" + triangle_down + '\'' +
                ", triangle_up='" + triangle_up + '\'' +
                '}';
    }
}
