package twisk.mondeIG;

import twisk.designPattern.SujetObserve;
import twisk.exceptions.*;
import twisk.outils.FabriqueIdentifiant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * La classe Monde.
 */
public class MondeIG extends SujetObserve {
    private final HashMap<String, EtapeIG> etapes;
    private final ArrayList<ArcIG> arcs;
    private String pdcId;
    private String style;

    /**
     * Instancie un nouveau Monde.
     */
    public MondeIG() {
        super();
        FabriqueIdentifiant fabriqueIdentifiant = FabriqueIdentifiant.getInstance();
        String idf = fabriqueIdentifiant.getIdentifiantEtape();
        etapes = new HashMap<>(10);
        etapes.put(idf, new ActiviteIG("Activite" + idf, idf));
        arcs = new ArrayList<>(10);
        pdcId = "null";
        style = "classique";
    }

    /**
     * Ajouter une nouvelle étape.
     *
     * @param type le type d'étape
     */
    public void ajouter(String type) {
        if (type.equals("Activite")) {
            FabriqueIdentifiant fabriqueIdentifiant = FabriqueIdentifiant.getInstance();
            String idf = fabriqueIdentifiant.getIdentifiantEtape();
            etapes.put(idf, new ActiviteIG("Activite" + idf, idf));
            notifierObservateur();
        }
    }

    /**
     * Retourne un iterateur sur les etapes.
     *
     * @return l'iterateur
     */
    public Iterator<EtapeIG> iterateurEtape() {
        return etapes.values().iterator();
    }

    /**
     * Retourne un iterateur sur les arc.
     *
     * @return l'iterateur
     */
    public Iterator<ArcIG> iterateurArc() {
        return arcs.iterator();
    }

    /**
     * Retourne l'étape à un certain indice.
     *
     * @param indice l'indice
     * @return l'étape
     */
    public EtapeIG getEtape(String indice) { //Fonction nécessaire aux tests
        return etapes.get(indice);
    }

    /**
     * Retourne le nombre d'étape.
     *
     * @return le nombre
     */
    public int nbEtapes() {
        return etapes.size();
    }

    /**
     * Ajoute un arc dans le monde à partir de 2 points de contrôles.
     *
     * @param pt1 le point de départ
     * @param pt2 le point d'arrivée
     * @throws TwiskException une de 3 exceptions
     */
    public void ajouter(PointDeControleIG pt1, PointDeControleIG pt2) throws TwiskException {
        boolean existeDeja = false;
        boolean departSurUnPointDarrivee = false;

        for (Iterator<ArcIG> it = this.iterateurArc(); it.hasNext(); ) {
            ArcIG a = it.next();
            if ((a.getPt1().getId().equals(pt1.getId()) && a.getPt2().getId().equals(pt2.getId()))) {
                existeDeja = true;
                break;
            }
            if (pt1.getId().equals(a.getPt2().getId()) || pt2.getId().equals(a.getPt1().getId())) {
                departSurUnPointDarrivee = true;
                break;
            }
        }

        if (pt1.getEtape() == pt2.getEtape())
            throw new MemeEtapeException("Un arc ne peut pas être formé\nentre deux points de contrôle de la même étape !");
        else if (existeDeja)
            throw new ExisteDejaException("L'arc que vous essayez de créer existe déjà !");
        else if (departSurUnPointDarrivee)
            throw new DepartSurArriveeException("Un arc ne peut pas avoir comme point de départ\nle point d'arrivée d'un autre arc !");
        else
            arcs.add(new ArcIG(pt1, pt2));
    }

    /**
     * Effectue une action lorsque l'on clique sur un pdc.
     *
     * @param pdc le pdc
     * @throws TwiskException une de 3 exceptions
     */
    public void cliqueSurPDC(PointDeControleIG pdc) throws TwiskException {
        if (pdcId.equals("null"))
            pdcId = pdc.getId();
        else {
            for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
                EtapeIG e = it.next();
                for (PointDeControleIG p : e)
                    if (pdcId.equals(p.getId())) {
                        pdcId = "null";
                        ajouter(p, pdc);
                        notifierObservateur();
                    }
            }
        }
    }

    /**
     * Retourne le nombre d'arcs.
     *
     * @return le nombre d'arcs
     */
//Fonctions utiles pour les tests
    public int getNbArcs() {
        return arcs.size();
    }

    /**
     * Sélectionne l'étape donnée.
     *
     * @param etape l'etape
     */
    public void etapeSelectionee(EtapeIG etape) {
        etape.inverseSelectionnee();
        notifierObservateur();
    }

    /**
     * Supprime la selection.
     */
    public void supprimerLaSelection() {
        for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
            supprimer(it);
        }
        supprimerLesArcsSelectionnes();
        notifierObservateur();
    }

    /**
     * Supprime l'étape que l'on trouve dans l'itérateur.
     *
     * @param it l'itérateur
     */
    public void supprimer(Iterator<EtapeIG> it) {
        EtapeIG e = it.next();
        if (e.estSelectionnee()) {
            for (Iterator<ArcIG> itA = this.iterateurArc(); itA.hasNext(); ) {
                ArcIG arc = itA.next();
                if (arc.estRelieAEtape(e)) {
                    itA.remove();
                    arcs.remove(arc);
                }
            }
            e.inverseSelectionnee();
            it.remove();
            etapes.remove(e.getIdentifiant());
        }
    }

    /**
     * Renomme l'étape selectionnée.
     *
     * @param nom le nom
     */
    public void renommerEtapeSelectionnee(String nom) {
        for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            if (e.estSelectionnee)
                e.setNom(nom);
        }
        effacerLaSelection();
    }

    /**
     * Retourne le nombre d'étapes sélectionnées
     *
     * @return le nombre
     */
    public int nbEtapesSelectionnees() {
        int nb = 0;
        for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            if (e.estSelectionnee)
                nb++;
        }
        return nb;
    }

    /**
     * Deplace l'etape envoyée.
     *
     * @param id l'identifiant de l'étape
     * @param x  la nouvelle position en x
     * @param y  la nouvelle position en y
     */
    public void deplacerEtape(String id, int x, int y) {
        getEtape(id).changePos(x, y);
    }

    /**
     * Sélectionne l'arc donné.
     *
     * @param arc l'arc
     */
    public void arcSelectione(ArcIG arc) {
        arc.inverseSelectionnee();
        notifierObservateur();
    }

    /**
     * Supprime les arcs selectionnes.
     */
    public void supprimerLesArcsSelectionnes() {
        for (Iterator<ArcIG> it = iterateurArc(); it.hasNext(); ) {
            ArcIG arc = it.next();
            if (arc.estSelectionnee()) {
                it.remove();
                arcs.remove(arc);
            }
        }
        notifierObservateur();
    }

    /**
     * Efface la sélection.
     */
    public void effacerLaSelection() {
        for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            if (e.estSelectionnee)
                e.inverseSelectionnee();
        }
        for (Iterator<ArcIG> it = iterateurArc(); it.hasNext(); ) {
            ArcIG arc = it.next();
            if (arc.estSelectionnee())
                arc.inverseSelectionnee();
        }
        notifierObservateur();
    }

    /**
     * Fait des étapes sélectionnées des entrées si elles n'en sont pas déjà.
     */
    public void gestionEntree() {
        for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            if (e.estSelectionnee)
                e.inverseEstUneEntree();
        }
        effacerLaSelection();
    }

    /**
     * Fait des étapes sélectionnées des sorties si elles n'en sont pas déjà.
     */
    public void gestionSorties() {
        for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            if (e.estSelectionnee)
                e.inverseEstUneSortie();
        }
        effacerLaSelection();
    }

    /**
     * Change le delai de l'étape selectionnée.
     *
     * @param s le délai
     * @throws ParamIncorrectsException renvoie une exception si le délai n'est pas correct
     */
    public void changerDelaiEtapeSelectionnee(String s) throws ParamIncorrectsException {
        int delai;
        try {
            delai = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ParamIncorrectsException("Delai donné n'est pas un Nombre !");
        }
        if (delai < 0)
            throw new ParamIncorrectsException("Delai donné négatif !");
        for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            if (e.estSelectionnee()) {
                if (delai < e.getEcart())
                    throw new ParamIncorrectsException("Delai donné inférieur à écart !");
                e.setDelai(delai);
            }
        }
        effacerLaSelection();
    }

    /**
     * Change l'écart de l'étape selectionnée.
     *
     * @param s l'écart
     * @throws ParamIncorrectsException renvoie une exception si l'écart n'est pas correct
     */
    public void changerEcartEtapeSelectionnee(String s) throws ParamIncorrectsException {
        int ecart;
        try {
            ecart = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ParamIncorrectsException("Ecart donné n'est pas un Nombre !");
        }
        if (ecart < 0)
            throw new ParamIncorrectsException("Ecart donné négatif !");
        for (Iterator<EtapeIG> it = iterateurEtape(); it.hasNext(); ) {
            EtapeIG e = it.next();
            if (e.estSelectionnee()) {
                if (ecart > e.getDelai())
                    throw new ParamIncorrectsException("Ecart donné supérieur à délai !");
                e.setEcart(ecart);
            }
        }
        effacerLaSelection();
    }

    /**
     * Définit le style de l'application.
     *
     * @param style le style
     */
    public void setStyle(String style) {
        this.style = style;
        notifierObservateur();
    }

    /**
     * Retourne le style de l'application.
     *
     * @return le style
     */
    public String getStyle() {
        return style;
    }
}
