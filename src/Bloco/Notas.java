package Bloco;

import javax.swing.*;

import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;

import java.io.*;
import java.awt.*;
import java.awt.event.*;


public class Notas extends JFrame{

	File arquivos;

	JTextArea area;
	JScrollPane js;
	boolean alterado;
	JMenuBar menuBar;

	JMenu arquivo;
	JMenuItem arquivoNovo;
	JMenuItem arquivoAbrir;
	JMenuItem arquivoSalvar;
	JMenuItem arquivoSair;

	JMenu ajuda;
	JMenuItem ajudaSobre;

	public Notas() {
		setTitle("Bloco de notas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo.png")));
		menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		setJMenuBar(menuBar);
		arquivo = new JMenu("Arquivo");
		menuBar.add(arquivo);
		arquivoNovo = new JMenuItem("Novo");
		arquivoNovo.setIcon(new ImageIcon(getClass().getResource("/icons/lightbulb.png")));
		arquivoNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(alterado==false) {
					arquivos = null;
					area.setText("");
				}else {
					int opt = JOptionPane.showConfirmDialog(null, "Deseja salvar o arquivo anterior??");
					if(opt==JOptionPane.YES_OPTION) {
						salvar();
					}else {
						arquivos = null;
						area.setText("");
					}
				}
				alterado=false;
			}
		});
		arquivo.add(arquivoNovo);
		arquivoAbrir = new JMenuItem("Abrir arquivo");
		arquivoAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirArquivo();
			}
		});
		arquivoAbrir.setIcon(new ImageIcon(getClass().getResource("/icons/code.png")));
		arquivo.add(arquivoAbrir);
		arquivoSalvar = new JMenuItem("Salvar");
		arquivoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		arquivoSalvar.setIcon(new ImageIcon(getClass().getResource("/icons/diskette.png")));
		arquivo.add(arquivoSalvar);
		
		arquivoSair = new JMenuItem("Sair");
		arquivoSair.setIcon(new ImageIcon(getClass().getResource("/icons/alert.png")));
		arquivoSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e ) {
				if(alterado==false) {
					dispose();
				}else {
					int opt = JOptionPane.showConfirmDialog(null, "Deseja salvar o arquivo anterior??");
					if(opt==JOptionPane.YES_OPTION) {
						salvar();
					}
				}
				
			}
		});
		arquivo.add(arquivoSair);

		ajuda = new JMenu("Ajuda");
		menuBar.add(ajuda);
		ajudaSobre = new JMenuItem("Sobre");
		ajudaSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Programa feito pelo aluno do IFB-Campus Brasília Joel Sena\n\n@joelee229");
			}
		});
		ajudaSobre.setIcon(new ImageIcon(getClass().getResource("/icons/key.png")));
		ajuda.add(ajudaSobre);

		area = new JTextArea();
		js = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		area.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				alterado=true;
			}
			
		});
		area.setFont(new Font("Arial",Font.BOLD,15));
		add(js);


	}

	
	


	public void abrirArquivo() {
		if(alterado!=true) {
			JFileChooser chooser = new JFileChooser();
			int opc = chooser.showOpenDialog(null);
			if(opc == JFileChooser.APPROVE_OPTION) {
				arquivos = chooser.getSelectedFile();
				byte[] arrayDeBytes = new byte[(int)arquivos.length()];
				try {
					FileInputStream fis = new FileInputStream(arquivos);
					fis.read(arrayDeBytes);
					String conteudo = new String(arrayDeBytes);
					area.setText(conteudo);
				}catch(FileNotFoundException fnfe) {
					JOptionPane.showMessageDialog(null, "O arquivo não foi encontrado!!!");
				}catch(IOException ioe) {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a leitura do arquivo!!!");
				}
			}
		}else {
			int opt = JOptionPane.showConfirmDialog(null, "Deseja salvar o arquivo anterior??");
			if(opt==JOptionPane.YES_OPTION) {
				salvar();
			}
		}
		alterado=false;
	}

	public void salvarComo() {
		JFileChooser jf = new JFileChooser();
		int opt = jf.showSaveDialog(null);
		if(opt ==JFileChooser.APPROVE_OPTION) {
			arquivos  = jf.getSelectedFile();
			salvar();
		}
		alterado=false;
	}

	public void salvar(){
		if(arquivos!=null) {
			String conteudo = new String(area.getText());
			byte[] arrayDeBytes = conteudo.getBytes();
			try {
				FileOutputStream fos = new FileOutputStream(arquivos);
				fos.write(arrayDeBytes);

			}catch(FileNotFoundException fnfe) {
				JOptionPane.showMessageDialog(null, "O arquivo não foi encontrado!!!");
			}catch(IOException ioe) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a escrita!!!");
			}
		}else {
			salvarComo();
		}
		alterado=false;
	}

	public static void main(String[] args) {
		Notas tela = new Notas();
		tela.setSize(700,600);
		tela.setLocationRelativeTo(null);
		tela.setVisible(true);
	}

}
