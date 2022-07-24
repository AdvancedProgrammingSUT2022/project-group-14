package Client.enums;

import Client.application.App;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public enum Avatars {
    IMG0(Objects.requireNonNull(App.class.getResource("/images/avatars/Alexander.png")).toString()),
    IMG1(Objects.requireNonNull(App.class.getResource("/images/avatars/Amanitore.png")).toString()),
    IMG2(Objects.requireNonNull(App.class.getResource("/images/avatars/Ambiorix.png")).toString()),
    IMG3(Objects.requireNonNull(App.class.getResource("/images/avatars/B3F_Tri.png")).toString()),
    IMG4(Objects.requireNonNull(App.class.getResource("/images/avatars/Basil_II.png")).toString()),
    IMG5(Objects.requireNonNull(App.class.getResource("/images/avatars/Catherine_de_Medici.png")).toString()),
    IMG6(Objects.requireNonNull(App.class.getResource("/images/avatars/Cleopatra.png")).toString()),
    IMG7(Objects.requireNonNull(App.class.getResource("/images/avatars/Cyrus.png")).toString()),
    IMG8(Objects.requireNonNull(App.class.getResource("/images/avatars/Dido.png")).toString()),
    IMG9(Objects.requireNonNull(App.class.getResource("/images/avatars/Eleanor_of_Aquitaine.png")).toString()),
    IMG10(Objects.requireNonNull(App.class.getResource("/images/avatars/Frederick_Barbarossa.png")).toString()),
    IMG11(Objects.requireNonNull(App.class.getResource("/images/avatars/Gandhi.png")).toString()),
    IMG12(Objects.requireNonNull(App.class.getResource("/images/avatars/Genghis_Khan.png")).toString()),
    IMG13(Objects.requireNonNull(App.class.getResource("/images/avatars/Gilgamesh.png")).toString()),
    IMG14(Objects.requireNonNull(App.class.getResource("/images/avatars/Gitarja.png")).toString()),
    IMG15(Objects.requireNonNull(App.class.getResource("/images/avatars/Gorgo.png")).toString()),
    IMG16(Objects.requireNonNull(App.class.getResource("/images/avatars/Hammurabi.png")).toString()),
    IMG17(Objects.requireNonNull(App.class.getResource("/images/avatars/Harald_Hardrada.png")).toString()),
    IMG18(Objects.requireNonNull(App.class.getResource("/images/avatars/Hojo_Tokimune.png")).toString()),
    IMG19(Objects.requireNonNull(App.class.getResource("/images/avatars/Jadwiga.png")).toString()),
    IMG20(Objects.requireNonNull(App.class.getResource("/images/avatars/Jayavarman_VII.png")).toString()),
    IMG21(Objects.requireNonNull(App.class.getResource("/images/avatars/Jo3Fo_III.png")).toString()),
    IMG22(Objects.requireNonNull(App.class.getResource("/images/avatars/John_Curtin.png")).toString()),
    IMG23(Objects.requireNonNull(App.class.getResource("/images/avatars/Kristina.png")).toString()),
    IMG24(Objects.requireNonNull(App.class.getResource("/images/avatars/Kublai_Khan.png")).toString()),
    IMG25(Objects.requireNonNull(App.class.getResource("/images/avatars/Kupe.png")).toString()),
    IMG26(Objects.requireNonNull(App.class.getResource("/images/avatars/Lady_Six_Sky.png")).toString()),
    IMG27(Objects.requireNonNull(App.class.getResource("/images/avatars/Lautaro.png")).toString()),
    IMG28(Objects.requireNonNull(App.class.getResource("/images/avatars/Mansa_Musa.png")).toString()),
    IMG29(Objects.requireNonNull(App.class.getResource("/images/avatars/Matthias_Corvinus.png")).toString()),
    IMG30(Objects.requireNonNull(App.class.getResource("/images/avatars/Menelik_II.png")).toString()),
    IMG31(Objects.requireNonNull(App.class.getResource("/images/avatars/Montezuma.png")).toString()),
    IMG32(Objects.requireNonNull(App.class.getResource("/images/avatars/Mvemba_a_Nzinga.png")).toString()),
    IMG33(Objects.requireNonNull(App.class.getResource("/images/avatars/Pachacuti.png")).toString()),
    IMG34(Objects.requireNonNull(App.class.getResource("/images/avatars/Pedro_II.png")).toString()),
    IMG35(Objects.requireNonNull(App.class.getResource("/images/avatars/Pericles.png")).toString()),
    IMG36(Objects.requireNonNull(App.class.getResource("/images/avatars/Peter.png")).toString()),
    IMG37(Objects.requireNonNull(App.class.getResource("/images/avatars/Philip_II.png")).toString()),
    IMG38(Objects.requireNonNull(App.class.getResource("/images/avatars/Poundmaker.png")).toString()),
    IMG39(Objects.requireNonNull(App.class.getResource("/images/avatars/Qin_Shi_Huang.png")).toString()),
    IMG40(Objects.requireNonNull(App.class.getResource("/images/avatars/Robert_the_Bruce.png")).toString()),
    IMG41(Objects.requireNonNull(App.class.getResource("/images/avatars/Saladin.png")).toString()),
    IMG42(Objects.requireNonNull(App.class.getResource("/images/avatars/Seondeok.png")).toString()),
    IMG43(Objects.requireNonNull(App.class.getResource("/images/avatars/Sim3Fn_Bol.png")).toString()),
    IMG44(Objects.requireNonNull(App.class.getResource("/images/avatars/Suleiman.png")).toString()),
    IMG45(Objects.requireNonNull(App.class.getResource("/images/avatars/Tamar.png")).toString()),
    IMG46(Objects.requireNonNull(App.class.getResource("/images/avatars/Teddy_Roosevelt.png")).toString()),
    IMG47(Objects.requireNonNull(App.class.getResource("/images/avatars/Tomyris.png")).toString()),
    IMG48(Objects.requireNonNull(App.class.getResource("/images/avatars/Trajan.png")).toString()),
    IMG49(Objects.requireNonNull(App.class.getResource("/images/avatars/Victoria.png")).toString()),
    IMG50(Objects.requireNonNull(App.class.getResource("/images/avatars/Wilfrid_Laurier.png")).toString()),
    IMG51(Objects.requireNonNull(App.class.getResource("/images/avatars/Wilhelmina.png")).toString());

    private final String address;

    Avatars(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public static ArrayList<String> getAllAddresses() {
        ArrayList<String> addresses = new ArrayList<>();
        for (Avatars value : Avatars.values()) {
            addresses.add(value.name());
        }
        return addresses;
    }

    public static boolean contains(String address) {
        for (Avatars value : Avatars.values()) {
            if (value.toString().equals(address))
                return true;
        }
        return false;
    }

    public Image getImage() {
        return new Image(this.address);
    }

}
