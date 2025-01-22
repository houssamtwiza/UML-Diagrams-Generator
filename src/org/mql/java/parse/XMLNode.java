package org.mql.java.parse;

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
			//une classe abstraite, on peut l'instancier par la methode suivante
					DocumentBuilderFactory factory= DocumentBuilderFactory.newDefaultNSInstance();
					//creer un document builder, builder permet de creer fichier xml et le remplir
					//methode de fabrication d'un objet pour creer un objet d'autres types, bin de fabrication 
			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document= builder.parse(source);
				//la racine
				node= document.getFirstChild();
				//dansla racine il y a  un elementnode
				while(node.getNodeType() != Node.ELEMENT_NODE) {
					node = node.getNextSibling(); //recuperer le frere de droit(suivant)
				}

		        if (node == null) {
		            throw new IllegalArgumentException("Aucun nœud élément trouvé dans le fichier XML.");
		        }
				
//				System.out.println(Node.ELEMENT_NODE);
//				System.out.println(Node.COMMENT_NODE);
			} catch (Exception e) {
				System.out.println("Erreur:"+ e.getMessage());
			}
		}
		//ue liste de child qu'on va convertir en tableaux
		public XMLNode[] children() {
			List<XMLNode> list= new Vector<XMLNode>();
			NodeList nl = node.getChildNodes();
			int n = nl.getLength();
			for(int i=0; i<n; i++) {
				Node child= nl.item(i);
				if(child.getNodeType() == Node.ELEMENT_NODE) {
					list.add(new XMLNode(child));
				}
			}
			//convertir la liste en tableau
			XMLNode t[] = new XMLNode[list.size()];
			list.toArray(t);
			return t;
				
		}
		
		public XMLNode child(String name) {
			List<XMLNode> list= new Vector<XMLNode>();
			NodeList nl = node.getChildNodes();
			int n = nl.getLength();
			for(int i=0; i<n; i++) {
				Node child= nl.item(i);
				if(child.getNodeName().equals(name)) {
					return new XMLNode(child);
				}
			}

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
			NamedNodeMap atts = node.getAttributes();
					return atts.getNamedItem(name) !=null? atts.getNamedItem(name).getNodeValue() : null;
					//remplacer le null contenir ou pas l'information, desoudre les problemes de null Option
					
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
