package org.mql.java.parse;

import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLNode {
	 private Node node;

		public XMLNode(Node node) {
			super();
			this.node = node;
		}
		public XMLNode(String source) {
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    try {
		        File xmlFile = new File(source);
		        if (!xmlFile.exists()) {
		            throw new IllegalArgumentException("Le fichier spécifié n'existe pas : " + source);
		        }
		        
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        Document document = builder.parse(xmlFile);

		        // Récupération de la racine
		        node = document.getFirstChild();
		        while (node != null && node.getNodeType() != Node.ELEMENT_NODE) {
		            node = node.getNextSibling();
		        }

		        if (node == null) {
		            throw new IllegalArgumentException("Aucun nœud élément trouvé dans le fichier XML.");
		        }
		    } catch (Exception e) {
		        System.err.println("Erreur de parsing XML : " + e.getMessage());
		        e.printStackTrace();
		    }
		}

//ue liste de child qu'on va convertir en tableaux
		// Retourne tous les enfants de type élément de ce nœud sous forme d'un tableau XMLNode
		public XMLNode[] children() {
		    // Vérification si le nœud courant est nul
		    if (node == null) {
		        return new XMLNode[0]; // Retourne un tableau vide
		    }

		    // Liste pour stocker les enfants de type élément
		    List<XMLNode> childrenList = new Vector<>();
		    NodeList childNodes = node.getChildNodes();
		    int numberOfChildren = childNodes.getLength();

		    // Parcours de tous les enfants
		    for (int i = 0; i < numberOfChildren; i++) {
		        Node child = childNodes.item(i);
		        // On ne conserve que les nœuds de type élément
		        if (child.getNodeType() == Node.ELEMENT_NODE) {
		            childrenList.add(new XMLNode(child));
		        }
		    }

		    // Conversion de la liste en tableau
		    return childrenList.toArray(new XMLNode[0]);
		}

		// Retourne un enfant spécifique basé sur son nom
		public XMLNode child(String name) {
		    // Vérification si le nœud courant est nul
		    if (node == null || name == null || name.isEmpty()) {
		        return null;
		    }

		    NodeList childNodes = node.getChildNodes();
		    int numberOfChildren = childNodes.getLength();

		    // Parcours de tous les enfants
		    for (int i = 0; i < numberOfChildren; i++) {
		        Node child = childNodes.item(i);
		        // Vérifie si le nom correspond et que c'est un nœud élément
		        if (child.getNodeType() == Node.ELEMENT_NODE && name.equals(child.getNodeName())) {
		            return new XMLNode(child);
		        }
		    }

		    // Retourne null si aucun nœud correspondant n'est trouvé
		    return null;
		}

		
		//recuprer le nom de noeud
		public String getName() {
			return node.getNodeName();
		}
		
		public String getValue() {
			// noeud element qui na pas de fils et sa valeur et son noeud textuel
			NodeList list= node.getChildNodes();
//			element a un seul fils ET SON TYPE ET TEXTE
			if(list.getLength()==1 && list.item(0).getNodeType()== Node.TEXT_NODE) {
				return list.item(0).getNodeValue();//gettextcontext
			}
			return null;
		}
		
		public String attribute(String name) {
		    if (node == null || node.getAttributes() == null) {
		        return null;
		    }
		    NamedNodeMap atts = node.getAttributes();
		    Node attNode = atts.getNamedItem(name);
		    return attNode != null ? attNode.getNodeValue() : null;
		}

		
		public int intAttribute(String name) {
			String att = attribute(name);
			try {
				return Integer.parseInt(att);
			}
			catch(Exception e) {
				return -1;
			}
		
		}
}
