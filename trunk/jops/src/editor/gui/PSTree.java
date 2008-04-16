package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.softmed.jops.Generator;
import org.softmed.jops.GeneratorBehaviour;
import org.softmed.jops.InfoObject;
import org.softmed.jops.ParticleBehaviour;
import org.softmed.jops.ParticleRender;
import org.softmed.jops.ParticleSystem;
import org.softmed.jops.PositionAnimator;
import org.softmed.jops.SpaceAnimator;
import org.softmed.jops.basic.BasicGenerator;
import org.softmed.jops.modifiers.Modifier;
import org.softmed.jops.space.GeneratorSpace;

import textures.TextureNotFoundException;
import textures.Textures;

public class PSTree implements TreeSelectionListener {

	ChoiceChanged choiceListener;

	JTree tree;

	JScrollPane pane;

	ParticleSystem ps;

	// ParticleManager pm;

	private Map<Generator, DefaultMutableTreeNode> generatorNodes = new HashMap<Generator, DefaultMutableTreeNode>();

	private Map<GeneratorSpace, DefaultMutableTreeNode> spaceNodes = new HashMap<GeneratorSpace, DefaultMutableTreeNode>();

	private Map<GeneratorBehaviour, DefaultMutableTreeNode> genBehaviourNodes = new HashMap<GeneratorBehaviour, DefaultMutableTreeNode>();

	private Map<ParticleBehaviour, DefaultMutableTreeNode> parBehaviourNodes = new HashMap<ParticleBehaviour, DefaultMutableTreeNode>();

	private Map<SpaceAnimator, DefaultMutableTreeNode> animatorNodes = new HashMap<SpaceAnimator, DefaultMutableTreeNode>();
	
	private Map<PositionAnimator, DefaultMutableTreeNode> panimatorNodes = new HashMap<PositionAnimator, DefaultMutableTreeNode>();

	private Map<ParticleRender, DefaultMutableTreeNode> renderNodes = new HashMap<ParticleRender, DefaultMutableTreeNode>();
	
	private Map<Modifier, DefaultMutableTreeNode> modifierNodes = new HashMap<Modifier, DefaultMutableTreeNode>();

	//private List<ParticleRender> renders;

	private List<Generator> gens;

	private List<GeneratorSpace> genSpaces;

	//private List<GeneratorBehaviour> genBehaviours;

	//private List<ParticleBehaviour> parBehaviours;

	//private List<SpaceAnimator> animators;
	
	//private List<PositionAnimator> panimators;
	
	
	
	//private List<Modifier> modifiers;

	private DefaultMutableTreeNode root;

	private DefaultMutableTreeNode renderRootNode;

	private DefaultMutableTreeNode generators;

	private DefaultMutableTreeNode spaces;

	private DefaultMutableTreeNode genBehaviourRootNode;

	private DefaultMutableTreeNode parBehaviourRootNode;

	private DefaultMutableTreeNode animatorRootNode;
	
	private DefaultMutableTreeNode panimatorRootNode;
	
	private DefaultMutableTreeNode modifierRootNode;

	// private List<GeneratorSpace> sps;

	private DefaultTreeModel treeModel;

	public PSTree() {
		root = new DefaultMutableTreeNode("Particle System");
		generators = new DefaultMutableTreeNode("Generators");
		spaces = new DefaultMutableTreeNode("Spaces");

		genBehaviourRootNode = new DefaultMutableTreeNode(
				"Generator Behaviours");
		parBehaviourRootNode = new DefaultMutableTreeNode("Particle Behaviours");
		animatorRootNode = new DefaultMutableTreeNode("Space Animators");
		panimatorRootNode = new DefaultMutableTreeNode("Position Animators");
		renderRootNode = new DefaultMutableTreeNode("Renders");
		modifierRootNode = new DefaultMutableTreeNode("Modifiers");

		root.add(generators);
		root.add(genBehaviourRootNode);
		root.add(parBehaviourRootNode);
		root.add(renderRootNode);
		root.add(spaces);
		root.add(panimatorRootNode);
		root.add(animatorRootNode);
		root.add(modifierRootNode);

		tree = new JTree(root);
		treeModel = (DefaultTreeModel) tree.getModel();
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);

		pane = new JScrollPane(tree);

		pane.setPreferredSize(new Dimension(260, 300));
		pane.setMaximumSize(new Dimension(260, 300));
		pane.setSize(new Dimension(260, 300));
//*/		
		//tree.setPreferredSize(new Dimension(260, 300));
		//tree.setMaximumSize(new Dimension(260, 300));
		//tree.setSize(new Dimension(260, 300));

		
		
		tree.setCellRenderer(new CustomTreeCellRenderer());

		InfoObjectEditor.PSTREE = this;
	}

	public void valueChanged(TreeSelectionEvent e) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		Object obj = null;

		if (node != null) {

			obj = node.getUserObject();
		}

		if (choiceListener != null)
			choiceListener.choosed(obj);

	}

	public void cleanAll() {
		tree.getSelectionModel().clearSelection();
		if (ps == null)
			return;

		ps.setAlive(false);
		ps.setRenderable(false);

		// remove generators
		gens = ps.getGenerators();

		List<Generator> uis = new ArrayList<Generator>();
		uis.addAll(gens);

		root.setUserObject(null);

		for (Generator elem : uis) {
			removeNode(elem);
		}

		// remove spaces
		genSpaces = ps.getSpaces();

		List<GeneratorSpace> suis = new ArrayList<GeneratorSpace>();
		suis.addAll(genSpaces);

		for (GeneratorSpace elem : suis) {
			removeNode(elem);
		}

		List<GeneratorBehaviour> gensb = new ArrayList<GeneratorBehaviour>();
		gensb.addAll(ps.getGenBehaviours());

		for (GeneratorBehaviour elem : gensb) {
			removeNode(elem);
		}

		List<ParticleBehaviour> parsb = new ArrayList<ParticleBehaviour>();
		parsb.addAll(ps.getBehaviours());

		for (ParticleBehaviour elem : parsb) {
			removeNode(elem);
		}

		List<SpaceAnimator> anms = new ArrayList<SpaceAnimator>();
		anms.addAll(ps.getAnimators());

		for (SpaceAnimator elem : anms) {
			removeNode(elem);
		}

		List<PositionAnimator> panms = new ArrayList<PositionAnimator>();
		panms.addAll(ps.getPanimators());

		for (PositionAnimator elem : panms) {
			removeNode(elem);
		}

		
		List<ParticleRender> rs = new ArrayList<ParticleRender>();
		rs.addAll(ps.getRenders());

		for (ParticleRender elem : rs) {
			removeNode(elem);
		}
		
		List<Modifier> ms = new ArrayList<Modifier>();
		ms.addAll(ps.getModifiers());

		for (Modifier elem : ms) {
			removeNode(elem);
		}

	}

	public void build() {

		generators.removeAllChildren();

		spaces.removeAllChildren();

		renderRootNode.removeAllChildren();
		genBehaviourRootNode.removeAllChildren();
		parBehaviourRootNode.removeAllChildren();
		animatorRootNode.removeAllChildren();
		panimatorRootNode.removeAllChildren();
		modifierRootNode.removeAllChildren();

		tree.getSelectionModel().clearSelection();

		root.setUserObject(ps);

		System.out.println("Found " + ps.getRenders().size() + " Generator");
		for (Generator elem : ps.getGenerators()) {
			addNode(elem);
		}

		System.out.println("Found " + ps.getRenders().size() + " GeneratorSpace");
		for (GeneratorSpace elem : ps.getSpaces()) {
			addNode(elem);
		}

		System.out.println("Found " + ps.getRenders().size() + " GeneratorBehaviour");
		for (GeneratorBehaviour elem : ps.getGenBehaviours()) {
			addNode(elem);
		}

		System.out.println("Found " + ps.getRenders().size() + " ParticleBehaviour");
		for (ParticleBehaviour elem : ps.getBehaviours()) {
			addNode(elem);
		}

		System.out.println("Found " + ps.getRenders().size() + " SpaceAnimator");
		for (SpaceAnimator elem : ps.getAnimators()) {
			addNode(elem);
		}
		
		System.out.println("Found " + ps.getRenders().size() + " PositionAnimator");
		for (PositionAnimator elem : ps.getPanimators()) {
			addNode(elem);
		}

		System.out.println("Found " + ps.getRenders().size() + " Renders");
		for (ParticleRender elem : ps.getRenders()) {
			addNode(elem);
		}
		
		System.out.println("Found " + ps.getModifiers().size() + " Modifiers");
		for (Modifier elem : ps.getModifiers()) {
			addNode(elem);
		}


	}

	public void setPs(ParticleSystem ps) {
		cleanAll();
		this.ps = ps;
		build();

		ps.setAlive(true);
		ps.setRenderable(true);

		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	public void setChoiceListener(ChoiceChanged choiceListener) {
		this.choiceListener = choiceListener;
	}

	public void addNode(Object obj) {
		if (obj instanceof Generator)
			addNode(obj, generators, generatorNodes);
		else if (obj instanceof GeneratorSpace)
			addNode(obj, spaces, spaceNodes);
		else if (obj instanceof GeneratorBehaviour)
			addNode(obj, genBehaviourRootNode, genBehaviourNodes);
		else if (obj instanceof ParticleBehaviour)
			addNode(obj, parBehaviourRootNode, parBehaviourNodes);
		else if (obj instanceof SpaceAnimator)
			addNode(obj, animatorRootNode, animatorNodes);
		else if (obj instanceof PositionAnimator)
			addNode(obj, panimatorRootNode, panimatorNodes);
		else if (obj instanceof ParticleRender)
			addNode(obj, renderRootNode, renderNodes);
		if (obj instanceof Modifier)
			addNode(obj, modifierRootNode, modifierNodes);

	}

	@SuppressWarnings("unchecked")
    protected void addNode(Object elem, DefaultMutableTreeNode group, Map nodes) {
		String name = ((InfoObject) elem).getName();
		name += "-" + elem.getClass().getSimpleName();
		DefaultMutableTreeNode temp = new DefaultMutableTreeNode(name);
		temp.setUserObject(elem);

		treeModel.insertNodeInto(temp, group, group.getChildCount());
		nodes.put(elem, temp);
	}

	public void removeNode(Object obj) {
		if (obj instanceof Generator){
			Editor.setDirty(true);
			removeObject(obj, generatorNodes, ps.getGenerators());
		}else if (obj instanceof GeneratorSpace){
			Editor.setDirty(true);
			removeObject(obj, spaceNodes, ps.getSpaces());
			ps.removeSpace((GeneratorSpace) obj);
		}else if (obj instanceof GeneratorBehaviour){
			Editor.setDirty(true);
			removeObject(obj, genBehaviourNodes, ps.getGenBehaviours());
			ps.removeGenB((GeneratorBehaviour) obj);
		}else if (obj instanceof ParticleBehaviour){
			Editor.setDirty(true);
			removeObject(obj, parBehaviourNodes, ps.getBehaviours());
			ps.removeParB((ParticleBehaviour) obj);
		}else if (obj instanceof SpaceAnimator){
			Editor.setDirty(true);
			removeObject(obj, animatorNodes, ps.getAnimators());
			ps.removeAnimator((SpaceAnimator) obj);
		}else if (obj instanceof PositionAnimator){
			Editor.setDirty(true);
			removeObject(obj, panimatorNodes, ps.getPanimators());
			ps.removePositionAnimator((PositionAnimator) obj);
		}else if (obj instanceof ParticleRender){
			Editor.setDirty(true);
			removeObject(obj, renderNodes, ps.getRenders());
			ps.removeRender((ParticleRender) obj);
		}else if (obj instanceof Modifier){
			Editor.setDirty(true);
			removeObject(obj, modifierNodes, ps.getModifiers());
			ps.removeModifier((Modifier) obj);
		}
	}

	protected void removeObject(Object elem, Map nodes, List ps) {
		DefaultMutableTreeNode temp = (DefaultMutableTreeNode) nodes.get(elem);

		if (temp == null)
			return;

		treeModel.removeNodeFromParent(temp);
		if (ps != null)
			ps.remove(elem);
		tree.getSelectionModel().clearSelection();

		tree.invalidate();
		tree.revalidate();
		tree.repaint();
	}

	// WORKING !!!!
	public Generator addNewDefaultGenerator() {
		Generator gen = new BasicGenerator();
		gen.getRender().setTextureName("particle.png");
		int id = 0;
		try {
			id = Textures.requestTexture(gen.getRender().getTextureName(), false);
		} catch (TextureNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gen.getRender().setTextureId(id);
		ps.addGenerator(gen);

		//ps.rebuild();

		 //build();
		// addNode(gen);

		//setPs(ps);
		addNode(gen);
		addNode(gen.getGb());
		addNode(gen.getPb());
		addNode(gen.getSpace());
		addNode(gen.getAnimator());
		addNode(gen.getPositionAnimator());
		addNode(gen.getRender());

		expandToNode(generatorNodes.get(gen));

		return gen;
	}

	public void addGenBehaviour() {
		GeneratorBehaviour gen = new GeneratorBehaviour();
		ps.getGenBehaviours().add(gen);
		// ps.rebuild();
		addNode(gen);

		expandToNode(genBehaviourNodes.get(gen));
	}

	public void addParBehaviour() {
		ParticleBehaviour gen = new ParticleBehaviour();
		ps.getBehaviours().add(gen);
		// ps.rebuild();
		addNode(gen);

		expandToNode(parBehaviourNodes.get(gen));

	}

	public void addAnimator() {
		SpaceAnimator gen = new SpaceAnimator();
		ps.getAnimators().add(gen);
		// ps.rebuild();
		addNode(gen);

		expandToNode(animatorNodes.get(gen));

	}

	public void addRender() {
		ParticleRender gen = new ParticleRender();
		ps.getRenders().add(gen);
		
		int id;
		try {
			id = Textures.requestTexture(gen
					.getTextureName(), false);
			gen.setTextureId(id);
		} catch (TextureNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ps.rebuild();
		addNode(gen);

		expandToNode(renderNodes.get(gen));

	}

	public void addSpace(GeneratorSpace space) {
		ps.getSpaces().add(space);
		// ps.rebuild();
		addNode(space);

		expandToNode(spaceNodes.get(space));

	}
	
	public void addPositionAnimator() {
		PositionAnimator panimator = new PositionAnimator();
		ps.getPanimators().add(panimator);
		// ps.rebuild();
		addNode(panimator);

		expandToNode(panimatorNodes.get(panimator));
	}


	
	public void addModifier(Modifier modifier) {
		ps.getModifiers().add(modifier);
		// ps.rebuild();
		addNode(modifier);

		expandToNode(modifierNodes.get(modifier));
		
	}
	
	protected void expandToNode(DefaultMutableTreeNode node) {
		if (node == null)
			return;
		TreePath path = new TreePath(node.getPath());
		tree.getSelectionModel().setSelectionPath(path);
	}

	public void addNewEmpyGenerator() {
		Generator gen = new BasicGenerator();
		gen.setGb(null);
		gen.setPb(null);
		gen.setAnimator(null);
		gen.setPositionAnimator(null);
		gen.setSpace(null);
		gen.setRender(null);
		ps.getGenerators().add(gen);
		ps.rebuild();
		addNode(gen);

		expandToNode(generatorNodes.get(gen));

	}

	public Object getSelectedObject() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		Object obj = null;

		if (node != null) {

			obj = node.getUserObject();
			return obj;
		} else
			return null;
	}

	public JScrollPane getPane() {
		return pane;
	}

	public JTree getTree() {
		return tree;
	}

	public ChoiceChanged getChoiceListener() {
		return choiceListener;
	}

	public ParticleSystem getPs() {
		return ps;
	}

	public void refreshNode(InfoObject info) {

		DefaultMutableTreeNode node = null;

		if (info instanceof Generator)
			node = generatorNodes.get(info);
		else if (info instanceof GeneratorSpace)
			node = spaceNodes.get(info);
		else if (info instanceof GeneratorBehaviour)
			node = genBehaviourNodes.get(info);
		else if (info instanceof ParticleBehaviour)
			node = parBehaviourNodes.get(info);
		else if (info instanceof SpaceAnimator)
			node = animatorNodes.get(info);
		else if (info instanceof PositionAnimator)
			node = panimatorNodes.get(info);
		else if (info instanceof ParticleRender)
			node = renderNodes.get(info);
		if (info instanceof Modifier)
			node = modifierNodes.get(info);
		
		if (node != null){
			((DefaultTreeModel) tree.getModel()).nodeChanged(node);
			pane.revalidate();
			pane.repaint();
		}

	}

	public void setSelectedPath(Object info){
		if(info == null)
			return;
		
		DefaultMutableTreeNode node = null;
		
		if (info instanceof Generator)
			node = generatorNodes.get(info);
		else if (info instanceof GeneratorSpace)
			node = spaceNodes.get(info);
		else if (info instanceof GeneratorBehaviour)
			node = genBehaviourNodes.get(info);
		else if (info instanceof ParticleBehaviour)
			node = parBehaviourNodes.get(info);
		else if (info instanceof SpaceAnimator)
			node = animatorNodes.get(info);
		else if (info instanceof PositionAnimator)
			node = panimatorNodes.get(info);
		else if (info instanceof ParticleRender)
			node = renderNodes.get(info);

		if (node != null)
		tree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
	}

	
	
	
}
