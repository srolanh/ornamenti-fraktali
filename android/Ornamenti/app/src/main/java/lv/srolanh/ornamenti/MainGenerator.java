package lv.srolanh.ornamenti;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by srolanh on 16.6.4.
 */
public class MainGenerator {

    ArrayList genNet(int size, boolean inverse) {
        int color = inverse ? 0 : 1; // norāda, vai tīklā jāliek 1 vai 0
        ArrayList<ArrayList<Integer>> net = new ArrayList<ArrayList<Integer>>(); // tīkla sākums
        net.add(new ArrayList<Integer>());
        net.add(new ArrayList<Integer>());
        if (inverse) {
            net.get(0).set(0, 1);
        } else {
        }
        while (net.get(0).size() < size) { // pirmā rinda
            if (color == 1) {
                net.get(0).addAll(Arrays.asList(1,1)); // liek tīklā 1
                color = 0; // pārslēdzas uz otru krāsu
            } else {
                net.get(0).addAll(Arrays.asList(0,0)); // liek tīklā 0
                color = 1; // pārslēdzas uz otru krāsu
            }
        }
        color = inverse ? 1 : 0; // otrās rindas sākums
        while (net.get(1).size() < size) {
            if (color == 1) {
                net.get(1).addAll(Arrays.asList(1, 1));
                color = 0;
            } else {
                net.get(1).addAll(Arrays.asList(0, 0));
                color = 1;
            }
        }
        if (net.get(0).size() > size) { // ja tīkls par 1 lielāks nekā vajag
            net.get(0).remove(net.get(0).size()); // noņemt elementu no pirmās rindas beigām
            net.get(1).remove(net.get(0).size()); // noņemt elementu no otrās rindas beigām
        }
        return net;
    }

    // ģenerē ornamentu
    ArrayList genFractal(ArrayList<ArrayList<Integer>> prevImage, boolean inverse, int level, Boolean repeatMiddle) {
        if (repeatMiddle == null) {
            repeatMiddle = true;
        }
        ArrayList<ArrayList<Integer>> image = (ArrayList) prevImage.clone(); // izveido rakstāmu kopiju
        int middle = (int) Math.floor(prevImage.size() / 2); // norāda vidu, kas atkārtojams divreiz
        int rowLength = prevImage.get(0).size() * 2; // norāda vajadzīgo rindas garumu šim līmenim
        int i;
        int j;
        for (i = prevImage.size() - 1; i >= 0; i--) {
            if (image.get(i).size() == rowLength) {
                continue;
            }
            for (j = prevImage.get(i).size() - 1; j >= 0; j--) { // cikls pār katras rindas image[i] kartru elementu image[i][j]
                image.get(i).add(j, prevImage.get(i).get(j)); // atkārto katru elementu divreiz
            }
        }
        if (level > 1 && repeatMiddle) {
            image.add(middle + 1, image.get(middle)); // atkārto vidu divreiz
        }
        ArrayList<ArrayList<Integer>> net = genNet(image.get(1).size(), inverse); // ģenerē tīklu ornamentam
        int netIndex = 0; // norāda, kura tīkla rinda jāievieto
        for (i = image.size() - 1; i >= 0; i--) {
            image.add(i, (ArrayList) net.get(netIndex).clone()); // ievieto vajadzīgo tīkla rindu
            if (netIndex == 0) {
                netIndex = 1;
            } else {
                netIndex = 0;
            }
        }
        if (repeatMiddle) {
            image.add((ArrayList) net.get(netIndex == 1 ? 0 : 1).clone());
        } else {
            image.add((ArrayList) net.get(netIndex).clone());
        }
        return image;
    }

// māras ugunskrusts
/*i = [
[0,0,0,0,0,1,0,0,0,0],
[0,0,0,0,1,1,0,0,0,0],
[0,0,1,1,1,0,0,1,0,0],
[0,0,0,1,1,0,1,1,0,0],
[1,1,0,0,1,1,1,1,1,0],
[0,1,1,1,1,1,0,0,1,1],
[0,0,1,1,0,1,1,0,0,0],
[0,0,1,0,0,1,1,1,0,0],
[0,0,0,0,1,1,0,0,0,0],
[0,0,0,0,1,0,0,0,0,0]
];*/

// ugunskrusts
// i = [[0,0,1,0],[1,1,1,0],[0,1,1,1],[0,1,0,0]]

// ķieģelis
// i = [[0,1,1,0],[0,1,1,0]];

}
