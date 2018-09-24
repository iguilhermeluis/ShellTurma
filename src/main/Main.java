package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws java.io.IOException{
		
		String commandLine;
		BufferedReader console = new BufferedReader
				(new InputStreamReader(System.in));
		
		ProcessBuilder pb = new ProcessBuilder();
		List<String> historico = new ArrayList<String>();
		int index = 0;
		//paramos no <ctrl c>	
		while(true){
			//lendo o que o usuário digitou
			System.out.print("msm>");
			commandLine = console.readLine();
			
			//o que foi digitado é salvo em uma lista(comandos) 
			String[] comandos = commandLine.split(" ");
			List<String> listaComandos = new ArrayList<String>();
			
			
			//Loop que verifica se salvou o que digitou foi parseado
			for(int i = 0;i<comandos.length;i++){
				//System.out.println(comandos[i]); ***verificador do parse***
				listaComandos.add(comandos[i]);
				
			}
			//System.out.print(listaComandos); ***verifica se adicionou corretamente***
			historico.addAll(listaComandos);
			try{
				//mostra o histórico no shell
				if(listaComandos.get(listaComandos.size()-1).equals("historico")){
					for(String s : historico)
					System.out.println((index++) + " " + s);
					continue;
				}
				
			//muda o diretório 
			
			if(listaComandos.contains("cd")){
				if(listaComandos.get(listaComandos.size()-1).equals("cd")){
					File home = new File(System.getProperty("user.home"));
					//testando para ver se o user.home mudou
					//System.out.println("O diretório base é " + home);
					pb.directory(home);
					continue;
				
				}else{
					String dir = listaComandos.get(1);
					//Verificando se o diretório foi repassado
					//System.out.println("O diretório mudou para " + dir);		
					File newPath = new File(dir); 
					boolean exists = newPath.exists();
					
					if(exists){
					System.out.println("/" + dir); //adicionada a "/" para limpar a saida na tela
					pb.directory(newPath);
					continue;
					}
				else{   //se o riretório não existir
						System.out.print("Path ");
					}
				}
			}
			
			if(listaComandos.contains("dir")){
				File diretoriomaior = new File("c://");
				if(diretoriomaior.isDirectory()) {
					String[] diretoriomenor = diretoriomaior.list();
					if(diretoriomenor != null) {
						for(String diretorio : diretoriomenor){
							System.out.println("              " + File.separator  + diretorio);
						}
						System.out.println("");
					} 
				}
				continue;
			}
			
				//Verificando se o home foi  mudado 
//			File home = new File(System.getProperty("user.home"));
//			pb.directory(home);
			
			
			
			//!! o comando retorna ao último comando útil
			if(listaComandos.get(listaComandos.size()-1).equals("!!")){
				pb.command(historico.get(historico.size()-2));
				
			}
			else if			
			(listaComandos.get(listaComandos.size()-1).charAt(0) == '!'){
				int b = Character.getNumericValue(listaComandos.get(listaComandos.size()-1).charAt(1));
				if(b<=historico.size())//verifica se o inteiro é maior que o tamanho da lista de comandos
				pb.command(historico.get(b));
			}
			else{
			pb.command(listaComandos);
			}
			
			Process process = pb.start();
			
			//obtém a entrada do input
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			//lê o processo do input
			String line;
			while((line = br.readLine()) != null)
				System.out.println(line);
			br.close();
			
			
			
			//se o usuário digitar enter, o processo continua
			if(commandLine.equals(" "))
				continue;
			}
			
			//recebe a exceção, exibe mensagem caso haja erro, retorna continuando no aguardo do usuário digitar
			catch (IOException e){
				System.out.println("Input Error, Please try again!");
			}
			
			/** The steps are:
			 * 1. parse the input to obtain the command and any parameters
			 * 2. create a ProcessBuilder object
			 * 3. start the process
			 * 4. obtain the output stream
			 * 5. output the contents returned by the command
			 */
		
		}

	}

}
