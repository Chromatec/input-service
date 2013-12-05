package inputservice.printerLib;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;

import rego.PrintLib.mobilePrinter;


public class NfePrinterA7 extends Printer{
	/** arrays for code128 */
	String testStringStart = "START ";
	String testStringFinish = "FINISH ";
	private byte[] changeFont = new byte[3];

	public int[] ascii = { 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44,
			45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
			62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
			79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95,
			96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
			110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122,
			123, 124, 125, 126, 200, 201, 202, 203, 204, 205, 206, 207, 208,
			209, 210, 211 };// 107 positions
	
	//private String doubleWidthFontOn = "!)";
	//private String defaultFont = "!	";
	//private String template1Name = "PRODUTOS";
	//private String template2Name = "PRODUTOS2";
	//private String template3Name = "IMPOSTOS";
	
	public String[] binary = { "11011001100", "11001101100", "11001100110",
			"10010011000", "10010001100", "10001001100", "10011001000",
			"10011000100", "10001100100", "11001001000", "11001000100",
			"11000100100", "10110011100", "10011011100", "10011001110",
			"10111001100", "10011101100", "10011100110", "11001110010",
			"11001011100", "11001001110", "11011100100", "11001110100",
			"11101101110", "11101001100", "11100101100", "11100100110",
			"11101100100", "11100110100", "11100110010", "11011011000",
			"11011000110", "11000110110", "10100011000", "10001011000",
			"10001000110", "10110001000", "10001101000", "10001100010",
			"11010001000", "11000101000", "11000100010", "10110111000",
			"10110001110", "10001101110", "10111011000", "10111000110",
			"10001110110", "11101110110", "11010001110", "11000101110",
			"11011101000", "11011100010", "11011101110", "11101011000",
			"11101000110", "11100010110", "11101101000", "11101100010",
			"11100011010", "11101111010", "11001000010", "11110001010",
			"10100110000", "10100001100", "10010110000", "10010000110",
			"10000101100", "10000100110", "10110010000", "10110000100",
			"10011010000", "10011000010", "10000110100", "10000110010",
			"11000010010", "11001010000", "11110111010", "11000010100",
			"10001111010", "10100111100", "10010111100", "10010011110",
			"10111100100", "10011110100", "10011110010", "11110100100",
			"11110010100", "11110010010", "11011011110", "11011110110",
			"11110110110", "10101111000", "10100011110", "10001011110",
			"10111101000", "10111100010", "11110101000", "11110100010",
			"10111011110", "10111101110", "11101011110", "11110101110",
			"11010000100", "11010010000", "11010011100", "1100011101011" };
	
	public int arrayLineSize = 0;// tamanho do array usado para imprimir dados
									// no quadrado
	//54 spaces for characters (blank spaces included)
	public static final String[] templateHeader = {" Recebemos da Empresa",
			" #empresa",
			" NF-e: #nfe",
			" Serie: #serie",
			" Identificacao/Assinatura     ",
			"                                           ",
			" ___/___/_____     _________________________"};

	public static final String[] templateHeader1 = {"Recebemos da                                         ",
			" #empresa", " #2empresa",
			"                                NF-e: #nfe",
			"                                Serie: #serie",
			"                   Identificacao/Assinatura",
			"                                           ",
			" ___/___/_____     _________________________" };
	

	public static final String[] templateSubHeader = {
			"         Danfe Simplificado          #tipo      ",
			"         Documento Auxiliar da       NF-e #nfe  ",
			"         		Nota Fiscal Eletronica      Serie #ser ",
			"        										   " };


	public static final String[] templateNatOp = { " Nat. Op: #natop" };

	public static final String[] templateNatOp1 = { " Nat. Op: #natop",
			" #2natop" };

	public static final String[] templateAccessKey = { " CHAVE DE ACESSO",
			" #key" };

	public static final String[] templateEmitter = { " EMITENTE:", " #empresa",
			" Rua #rua", " #cides", " CEP #cep TEL #tel", " CNPJ #cnpj",
			" IE #ie" };

	public static final String[] templateReceiver = {
			" DESTINATARIO:                    | EMISSAO  ",// 34//10
			" $#dest|#dtemi    ", " $#end|  SAIDA   ", " $#cides|#dtsaida  ",
			" CEP #cep00000 TEL $#tel|HORA SAIDA", " CNPJ $#cnpj|#horsaida",
			" IE $#ie|         " };
	
	/**
	 * 
	 * TEMPLATE DOS PRODUTOS - PRODUCT TEMPLATES
	 * 
	 **/
	
	//TEMPLATE 001
	public static final Boolean[] activatedTemplates = {true, true, true};
	public static final String[] templateProduct1 = {
			" Descricao | un | qtde | Valores |  ICMS  | CST | Desc  |  Valor    ",
			"  Produto  |    |      |   unit  |        |     |       |  Total    ",
			"___________|____|______|_________|________|_____|_______|___________",
			"#val01 | #val02 | #val03 | #val04 | #val05  |#val06 |#val07| #val08     ",
			"           |    |      |         |        |     |       |            ",
			"           |    |      |         |        |     |       |            "
	};
	// $ces 10+1 prod, 2+2 u ,4+2 qtd ,7+2 val0, 6+2 icms, 3+2 cst, 3+2 desc, 10+2 = 60 valT // Tem que dar 60, no máximo
	public static final String linePro1 = "#val01 | #val02 | #val03 | #val04 | #val05 | #val06 | #val07 | #val08 ";
	public static final String[] tagsTemplate1Content = {"#val01", "#val02", "#val03", "#val04", "#val05", "#val06", "#val07", "#val08" };
	public static final int[] sizesTemplate1 = { 10, 2, 4, 7, 6, 3, 5, 10 };// numero de caracteres que cabem em cad tag/colula
	//	prod		 u	  qtd		val0	icms	cst		vltot
	public static final String lineBlankTemplate1 = "           |    |      |         |        |     |       |            ";
	
	//TEMPLATE 002
	public static final String[]templateProduct2 = {
			"123456789.123456789.123456789.123456789.123456789.123456789.12345678",
			"  Produto2 |    |      |  unit2  |        |     |       |  Total2   ",
			"___________|____|______|_________|________|_____|_______|___________",
			"#val01 | #val02 | #val03 | #val04 | #val05  |#val06 |#val07| #val08     ",
			"           |    |      |         |        |     |       |            ",
			"           |    |      |         |        |     |       |            "};
	public static final String linePro2 = "#val01 | #val02 | #val03 | #val04 | #val05 | #val06 | #val07 | #val08 ";
	public static final String[] tagsTemplate2Content = {"#val01", "#val02", "#val03", "#val04", "#val05", "#val06", "#val07", "#val08" };
	public static final int[] sizesTemplate2 = { 10, 2, 4, 7, 6, 3, 5, 10 };// se passar de 47, o programa nao imprime
	public static final String lineBlankTemplate2 = "           |    |      |         |        |     |       |            ";
	
	//TEMPLATE 003
	public static final String[]templateProduct3 = {
			" Base calc | Valor ICMS | B Calc     | V ICMS   | ICMS Total ",
			"   ICMS    |            | ICMS ST    |  Sub     |            ",
			"___________|____________|____________|__________|____________",
			"#val01 |#val02      |#val03|#val04   |#val05      ",
			"           |            |            |          |            ",
			"           |            |            |          |#totnota    "};
	public static final String linePro3 = "#val01 | #val02 | #val03 | #val04 | #val05 ";
	public static final String[] tagsTemplate3Content = {"#val01", "#val02", "#val03", "#val04", "#val05"};
	public static final int[] sizesTemplate3 = { 10, 10, 10, 7, 10};
	public static final String lineBlankTemplate3 = "           |            |             |               |            ";
	
	//public static final String totalCost = "Valor total da nota:                                   #totnota";
	
	public static final String[] tagsHeader = {"#empresa", "#nfe", "#serie"};
	public static final String[] tagsHeader1 = { "#empresa", "#2empresa",
			"#nfe", "#serie" };
	public static final String[] tagsSubHeader = {"#tipo", "#nfe", "#ser" };
	public static final String[] tagsNatOp = {"#natop" };
	public static final String[] tagsNatOp1 = {"#natop", "#2natop" };
	public static final String[] tagsAccessKey = {"#key" };
	public static final String[] tagsEmitter = {"#empresa", "#rua", "#cides",
			"#cep", "#tel", "#cnpj", "#ie" };
	public static final String[] tagsReceiver = {"#dest", "#dtemi", "#end",
			"#cides", "#dtsaida", "#cep00000", "#tel", "#cnpj", "#horsaida",
			"#ie" };
	
	public static final String[] spaces = new String[] { "", " ", "  ", "   ",
			"    ", "     ", "      ", "       ", "        ", "         ",
			"          " };
	public static final String defaultFont = "small";
	/**
	 * $ces - Cada item representa o número máximo de caracteres suportados em cada linha
	 * [0]33 = Nome da empresa
	 * [1]33 = Endereço
	 * [2]33 = Continuação do endereço
	 * [3]15 = CEP E TEL
	 * [4]28 = CNPJ
	 * [5]30 = IE
	 * Para a fonte menor (obs quando aumento esses valores, o template 2 não imprime):
	 * [6]35
	 * [7]35
	 * [8]35
	 * [9]20
	 * [10]33
	 * [11]35
	*/
	public static final int[] sizeslastitem = new int[] { 33, 33, 33, 15, 28,
			30, 33, 33, 33, 15, 28, 30 };// for filltemplatediv

	public NfePrinterA7(mobilePrinter mp) {
		super(mp);
	}

	public NfePrinterA7() {
		super();
	}

	/* calculate verification digit for data */
	public String calcDV(String data) {
		int sum = 0, weight = 2, DV;
		for (int i = 42; i >= 0; i--) {
			sum += Integer.valueOf(data.subSequence(i, i + 1).toString())
					* weight;
			if (weight == 9) {
				weight = 2;
			} else {
				weight++;
			}
		}
		DV = 11 - (sum % 11);
		data += String.valueOf(DV);
		// Log.d("printer",data);
		return data;
	}

	/* calculate verification digit for code128 */
	public int calcDV128(String data) {
		int sum = 105, position = 0, weight = 1;
		for (int i = 0; i < 22; i++) {
			sum += Integer.valueOf(data.substring(position, position + 2))
					* weight++;
			position += 2;
		}
		// Log.d("printer","sum"+sum+" dv"+String.valueOf(sum%103));
		return sum % 103;
	}

	/* generate array with ascii values of each group of two characters */
	private int[] genArrayDataAscii(String data) {
		int[] dataAscii = new int[25];
		int positionData = 0;
		data = calcDV(data);
		dataAscii[0] = ascii[105];
		dataAscii[23] = ascii[calcDV128(data)];
		dataAscii[24] = ascii[106];
		// Log.d("printer",data.substring(positionD!	ata, positionData+2) + " " +
		// " " + String.valueOf(dataAscii[0]) + " " +
		// String.valueOf(dataAscii[23]) + " " + String.valueOf(dataAscii[24]));
		/** arrays for code128 */

		for (int i = 1; i < 23; i++) {
			dataAscii[i] = ascii[Integer.valueOf(data.substring(positionData,
					positionData + 2))];
			positionData += 2;
			// Log.d("printer","i:"+String.valueOf(i) + " data: " +
			// String.valueOf(dataAscii[i]) );
		}
		/*
		 * for(int i=0;i<25;i++){ Log.d("printer",String.valueOf(dataAscii[i]));
		 * }
		 */
		return dataAscii;
	}

	/*
	 * method that receives dataAscii array and generates the data hole mode for
	 * horizontal
	 */
	private String genDataBinHor(int[] dataAscii) {
		String[] dataBin11 = new String[25];// bits agrupados em 11
		String holeDataBin = "";

		/* converts to binary and put in dataBin11 */
		for (int i = 0; i < 25; i++) {
			int pos = Arrays.binarySearch(ascii, dataAscii[i]);
			dataBin11[i] = binary[pos];
			holeDataBin += dataBin11[i];
		}
		return holeDataBin;
	}

	public void genAccessKey(String key) throws UnsupportedEncodingException {
		genAllByFields(NfePrinterA7.templateAccessKey,
				NfePrinterA7.tagsAccessKey, new String[] { calcDV(key) });
	}

	/*
	 * method that uses all previous methods to generate a barcode128 in
	 * horizontal with y = 6
	 */
	public void genBarCode128HorNRI6(String key) throws UnsupportedEncodingException {
		/**
		 * imprimir cabeçalho: Chave de acesso
		 */
		mobileprint.Reset();
		String dataBin;
		byte[] line = new byte[1688];
		dataBin = genDataBinHor(genArrayDataAscii(key));

		byte[] hex = { 0x00, (byte) 0xFF };

		line[0] = 0x1D;
		line[1] = '*';
		line[2] = 35;// x=2 ( Width 16 dots)
		line[3] = 6;// y=3 ( Height 24 dots)

		int startLine = 4;
		int length = dataBin.length();
		for (int i = 0; i < length + 2; i++) {
			for (int j = 0; j < 6; j++) {
				// Log.d("printer",String.valueOf(x));
				if (i == length || i == length + 1) {
					line[startLine++] = hex[0];
				} else {
					int x = Integer.parseInt(dataBin.substring(i, i + 1));
					line[startLine++] = hex[x];
				}
			}
		}

		// 840valores para y=3 -- 1680 para y=6
		
		//START - Código para impressão em modo Gráfico
		line[1684] = 0x1D;
		line[1685] = 0x2F;
		line[1686] = 0x03;
		//END
		mobileprint.SendBuffer(line, 1687);
		mobileprint.FeedLines(1);
		line = new byte[4];
		
		//START - Este código é o responsável pelo espaço entre os caracteres
		line[0] = 0x1B;
		line[1] = '3';
		line[2] = 32;// $ces O valor padrão é 8, 16 equivale a 2mm, 32 equivale a 4mm
						//É a margem da nota fiscal
		//END
		mobileprint.SendBuffer(line, 3);
		mobileprint.Reset();
		mobileprint.FormatString(false, false, true, false, false);
		try {
			mobileprint.SendString(" " + calcDV(key));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mobileprint.FeedLines(1);
	}

	/* preenche o array do quadrado a ser impresso com o topo do quadrado */
	
	public String templateValuesCalculations(){
		
		return "";
	}
	
	
	//Determines the top lines's width
	private byte[] fillTop(byte[] line, int ini) throws UnsupportedEncodingException {
		changeFont[0] = 0x1B;
		changeFont[1] = 0x21;
		changeFont[2] = 0x09;
		mobileprint.SendBuffer(changeFont, 3);
		for (int i = ini; i < ini + 70; i++) {// 47+0D+0A=49
			if (i == ini || i == ini + 69) {
				if (i == ini)
					line[i] = (byte) 0x7C;//0xDA//0xC9//Canto Esquerdo
				else
					line[i] = (byte) 0x7C;//0xBF//0x7C//0xF0//Canto Direito
			} else {
				line[i] = (byte) 0x3D;//0xC4//0x5F//Meio
			}
		}
		//Controles: 0x0D = Carriage return & 0x0A = New line feed
		line[ini + 70] = 0x0D;
		line[ini + 71] = 0x0A;
		arrayLineSize += 72;//this number changes it
		return line;
	}

	/* preenche o array do quadrado a ser impresso com o rodapé do quadrado */
	private byte[] fillBottom(byte[] line, int ini) throws UnsupportedEncodingException {
		for (int i = ini; i < ini + 70; i++) {
			if (i == ini || i == ini + 69) {
				if (i == ini){
					line[i] = (byte) 0x7C;//0xC0//0x7C//Canto Esquerdo
				}else{
					line[i] = (byte) 0x7C;//0x7C//0xD9//Canto Direito
				}
			} else {
				line[i] = (byte) 0x3D;//0xC4//Preenche o meio
			}
		}
		arrayLineSize += 70;//this number changes it
		return line;
	}

	/* preenche o array do quadrado a ser impresso com o conteúdo do quadrado */
	
	//Determines how many characters will fit into the square space -- NUM CHARACTERS CONTENT
	private byte[] fillContent(byte[] line, int ini, String[] content) throws UnsupportedEncodingException {
		/*
		 * for(int i =0 ; i<line.length;i++){
		 * Log.d("printer",String.valueOf(line[i])); }
		 */
		int iniPos = ini;// ini = arrayLineSize
		for (int i = 0; i < content.length; i++) {
			int iniPosLine = 0;
			for (int j = iniPos; j < iniPos + 70; j++) { // Era 47
				if (j == iniPos || j == iniPos + 69) { // Era 46 // Laterais do Quadrado
					//Log.d("iniPos = ", String.valueOf(iniPos)); //Descobrir qual é o valor dessa variável
					line[j] = (byte) 0x7C;//0xB3//0x7C//0x0BA
				} else {
					if (iniPosLine < content[i].length()) {
						if (content[i].charAt(iniPosLine) == '|'){
							line[j] = (byte) 0x7C;//0xB3//Barras verticais dos campos: Emitente/Destinatario/Produtos
						} else {
							line[j] = (byte) content[i].charAt(iniPosLine);
						}
						iniPosLine++;
					} else {
						line[j] = (byte) 0x20;
					}
				}
			}
			//Controles: 0x0D = Carriage return & 0x0A = New line feed
			line[iniPos + 70] = 0x0D;//Era 47
			line[iniPos + 71] = 0x0A;//Era 48
			arrayLineSize = iniPos += 72;//Era 49
			iniPosLine = 0;

		}
		return line;
	}

	/*
	 * dado um template, as tags e os valores (fixos) esta função substitui os
	 * valores pelas tags
	 */
	public String[] fillTemplate(String[] template, String empresa, int nfe,
			int serie) throws UnsupportedEncodingException {
		for (int i = 0; i < template.length; i++) {
			template[i] = template[i].replace("#empresa", empresa)
					.replace("#nfe", String.valueOf(nfe))
					.replace("#serie", String.valueOf(serie));
		}
		return template;
	}

	/*
	 * dado um template, as tags e os valores (variáveis) esta função substitui
	 * os valores pelas tags
	 */
	public String[] fillTemplate(String[] template, String[] tags, String[] data) {
		for (int i = 0; i < template.length; i++) {
			for (int j = 0; j < tags.length; j++) {
				template[i] = template[i].replace(tags[j], data[j]);
			}
		}
		return template;
	}

	private String fillNumSpaces(String data, int numSpaces) {
		int times = 0;
		if (numSpaces > 10) {
			times = numSpaces / 10;
			for (int i = 0; i < times; i++) {
				data += spaces[10];
			}
			data += spaces[numSpaces % 10];
		} else {
			data += spaces[numSpaces];
		}

		return data;
	}

	/*
	 * dado um template, as tags e os valores (variáveis) esta função substitui
	 * os valores pelas tags
	 */
	public String[] fillTemplateDiv(String[] template, String[] tags,
			int[] sizeslastitem, String[] data, int indexini) {
		// sizeslastitem = new int[]{33,33,33,15,28,30};//este array contem o
		// tamanho desde o inicio ultima tag até a barra
		// indexini = 1;//inicio da linha que possui tags para substituição
		// tags com marcador $ devem ser colocados espaços após
		int numSpaces = 0, $ = 0;// $ é a posição do array sizeslastitem
		
		if(defaultFont.equals("small")){
			$ = 6;
		}
		
		for (int i = indexini; i < template.length; i++) {
			for (int j = 0; j < tags.length; j++) {
				int index = template[i].indexOf("$" + tags[j]);
				if (template[i].charAt(index == -1 ? 0 : index) == '$') {
					numSpaces = sizeslastitem[$++] - data[j].length();
					data[j] = fillNumSpaces(data[j], numSpaces);
					template[i] = template[i].replace("$" + tags[j], data[j]);// +spaces[numSpaces]
				} else {
					template[i] = template[i].replace(tags[j], data[j]);
				}
			}
		}
		return template;
	}
	
	/*
	 * dado um template, as tags e os valores (variáveis) esta função substitui
	 * os valores pelas tags
	 */
	public List<String> fillTemplateProducts(String[] template, String[] tagsp,
			int[] sizesp, String[][] datap, String tot, String linePro,
			String lineBlank) throws UnsupportedEncodingException {
		List<String> fullTemplate = new ArrayList<String>();
		
		/*
		 * $ces -  essa variável inicia em 3 para que a substituição das tags dos produtos
		 * seja feita a partir do quarto índice dos templates
		 * */
		int ini = 3;
		
		boolean writeDown = true;

		int biggersize = 0;
		
		// $ces - Adiciona os três primeiros índices do Array "template"
		fullTemplate.add(template[0]);
		fullTemplate.add(template[1]);
		fullTemplate.add(template[2]);

		for (int i = 0; i < datap.length; i++) {
			biggersize = ini;
			/**
			 * $ces - era "j < 5" 
			 * Esse número do loop determina quantas colunas o código irá rastrear
			 * troquei por uma variável que contém o número de tags
			 * cadastradas em tagsProduct, isso faz com que o código
			 * mapeie todas as colunas automaticamente
			 */
			for (int j = 0; j < tagsp.length; j++) {
				
				/**$ces
				 * Esse laço preenche a linha dos Produtos
				 */	
				int itensize = datap[i][j].length();
				int num = (int) itensize / sizesp[j];
				num += (int) itensize % sizesp[j] != 0 ? 1 : 0;
				// fullTemplate.add(ini, linhaPro);
				for (int k = biggersize; k < ini + num; k++) {
					fullTemplate.add(biggersize, linePro);
					biggersize++; // ini3 e big 6
				}// agora bigsize terá a próxima posição útil, e ele menos ini
					// será a quantidade de linhas usadas
				int iniP = 0;
				for (int k = ini; k < biggersize; k++) {
					String data;
					// inicio da substring da palavra
					if (num > 1) {
						writeDown = true;
						if (k == biggersize - 1) {
							data = datap[i][j].substring(iniP);
							data += spaces[sizesp[j]
									- datap[i][j].substring(iniP).length()];
							/*$ces (VERIFIED) - Esse trecho abaixo preenche a linha do
										template dos Produtos (quando NÃO É necessária
										a quebra de linhas)
							*/
							fullTemplate.set(
									k,
									fullTemplate.get(k).replace(
													tagsp[j], data));
							//Log.d("lines","last "+fullTemplate.get(k)+
							//"spaces"+String.valueOf(sizesp[j]-datap[i][j].substring(iniP).length()));
						} else {
							/*$ces (VERIFIED) - Esse trecho abaixo preenche a linha do
										template dos Produtos (quando É necessária
										a quebra de linhas)
							 */
							fullTemplate.set(
									k,
									fullTemplate.get(k).replace(
											tagsp[j],
											datap[i][j].substring(iniP, iniP
													+ sizesp[j])));
							//Log.d("lines","not last "+fullTemplate.get(k) );
						}
					} else {
						/*$ces (CHECK)- Esse trecho abaixo preenche com espaços vazios a linha do
									template dos Produtos (quando É necessária
									a quebra de linhas)
						 */
						if (writeDown) {
							data = datap[i][j];
							data += spaces[sizesp[j] - datap[i][j].length()];
							fullTemplate
									.set(k,
											fullTemplate.get(k).replace(
													tagsp[j], data));
						} else {
							data = spaces[sizesp[j]];
							fullTemplate
									.set(k,
											fullTemplate.get(k).replace(
													tagsp[j], data));
						}
						writeDown = false;// se é permitido escrever na linha
											// abaixo
						// Log.d("lines","one "+fullTemplate.get(k) );
					}
					iniP += sizesp[j];
				}
				writeDown = true;
				// maiornum=maior;

			}
			fullTemplate.add(biggersize++, lineBlank);
			ini = biggersize;
		}
		fullTemplate.add(biggersize, template[5].replace("#totnota", tot));
		return fullTemplate;
	}

	/* gerar cabeçalho da nota com os campos variáveis a partir de layout */
	public void genNotaHeaderByFields(String[] template, String[] tags,
			String[] data) throws UnsupportedEncodingException {
		// area útil de cada linha é de 45 caracteres, 43 se ficar um espaço
		// entre o inicio e o fim.
		byte[] line = new byte[20000];

		// begin
		line[0] = (byte) 0x0D;// 0d
		line[1] = (byte) 0x0A;
		line[2] = (byte) 0x1B;
		line[3] = (byte) 0x33;
		line[4] = (byte) 0x06;
		line[5] = (byte) 0x0D;
		line[6] = (byte) 0x0A;
		arrayLineSize = 7; //7 //$ces Mudei para enviar mais comandos de impressão
		
		// top
		line = fillTop(line, arrayLineSize);
		// content
		template = fillTemplate(template, tags, data);
		/**/

		line = fillContent(line, arrayLineSize, template);
		// bottom
		line = fillBottom(line, arrayLineSize);

		// 0d carriage/0a line feed
		mobileprint.SendBuffer(line, arrayLineSize);
		mobileprint.FeedLines(1);

	}

	/* gerar cabeçalho da nota com os campos fixos a partir de layout */
	//MÉTODO QUE CONTROLA A IMPRESSÃO DOS CAMPOS: PRODUTO
	public void genNotaProductsByFields(String[] template, String[] tagsp,
			int[] sizesp, String[][] datap, String tot) throws UnsupportedEncodingException {
		// area útil de cada linha é de 45 caracteres, 43 se ficar um espaço
		// entre o inicio e o fim.
		byte[] line = new byte[20000];
		List<String> templateList;

		// begin
		line[0] = (byte) 0x0D;// 0d // Print and Carriage Return
		line[1] = (byte) 0x0A;//Print and line Feed
		line[2] = (byte) 0x1B;//ESC
		line[3] = (byte) 0x33;//Set line spacing
		line[4] = (byte) 0x06;//Acknowledge
		line[5] = (byte) 0x0D;//Print and Carriage Return
		line[6] = (byte) 0x0A;//Print and line Feed
		arrayLineSize = 7;
		// top
		line = fillTop(line, arrayLineSize);
		// content
		//fazer um if para checar qual template a ser impresso
		if(template.equals(templateProduct1)){
			mobileprint.FeedLines(1);
			//mobileprint.SendString(doubleWidthFontOn + template1Name + defaultFont);
			templateList = fillTemplateProducts(template, tagsp, sizesp, datap,
					tot, linePro1, lineBlankTemplate1);//linePro1 = linePro
			template = new String[templateList.size()];
			for (int i = 0; i < templateList.size(); i++) {
				template[i] = templateList.get(i);
			}
		} else if (template.equals(templateProduct2)) {
			mobileprint.FeedLines(1);
			//mobileprint.SendString(doubleWidthFontOn + template2Name + defaultFont);
			templateList = fillTemplateProducts(template, tagsp, sizesp, datap,
					tot, linePro2, lineBlankTemplate2);//linePro1 = linePro
			template = new String[templateList.size()];
			for (int i = 0; i < templateList.size(); i++) {
				template[i] = templateList.get(i);
			}
		} else {
			mobileprint.FeedLines(1);
			//mobileprint.SendString(doubleWidthFontOn + template3Name + defaultFont);
			templateList = fillTemplateProducts(template, tagsp, sizesp, datap,
					tot, linePro3, lineBlankTemplate3);//linePro1 = linePro
			template = new String[templateList.size()];
			for (int i = 0; i < templateList.size(); i++) {
				template[i] = templateList.get(i);
			}
		}
			
		line = fillContent(line, arrayLineSize, template);
		// bottom
		line = fillBottom(line, arrayLineSize);

		// 0d carriage/0a line feed
		mobileprint.SendBuffer(line, arrayLineSize);
		mobileprint.FeedLines(1);
	}

	/* gerar qualquer parte da nota com os campos a partir de layout */
	//MÉTODO QUE CONTROLA A IMPRESSÃO DOS CAMPOS: EMITENTE(EMISSOR)
	public void genAllByFields(String[] template, String[] tags, String[] data) throws UnsupportedEncodingException {
		// area útil de cada linha é de 45 caracteres, 43 se ficar um espaço
		// entre o inicio e o fim.
		byte[] line = new byte[20000];
		// type 0 = template sem barra divisória, e 1 com barra divisória

		// begin
		line[0] = (byte) 0x0D;// 0d
		line[1] = (byte) 0x0A;
		line[2] = (byte) 0x1B;
		line[3] = (byte) 0x33;
		line[4] = (byte) 0x06;
		line[5] = (byte) 0x0D;
		line[6] = (byte) 0x0A;
		arrayLineSize = 7;
		// top
		line = fillTop(line, arrayLineSize);
		// content
		template = fillTemplate(template, tags, data);
		line = fillContent(line, arrayLineSize, template);
		// bottom
		line = fillBottom(line, arrayLineSize);

		// 0d carriage/0a line feed
		mobileprint.SendBuffer(line, arrayLineSize);
		mobileprint.FeedLines(1);
	}

	/*
	 * gerar qualquer parte da nota com os campos a partir de layout tamanho dos
	 * itens no caso de ter barra divisória
	 */
	//MÉTODO QUE CONTROLA A IMPRESSÃO DOS CAMPOS: DESTINATÁRIO
	public void genAllByFields(String[] template, String[] tags, String[] data,
			int type, int[] sizeslastitem, int indexini) throws UnsupportedEncodingException {
		// area útil de cada linha é de 45 caracteres, 43 se ficar um espaço
		// entre o inicio e o fim.
		byte[] line = new byte[20000];
		// type 0 = template sem barra divisória, e 1 com barra divisória
		// indexini = inicio da linha que contem tags para substituição, somente
		// usada no tipo 2(type = 1).

		// begin
		line[0] = (byte) 0x0D;// 0d
		line[1] = (byte) 0x0A;
		
		line[2] = (byte) 0x1B;
		line[3] = (byte) 0x33;
		line[4] = (byte) 0x06;
		line[5] = (byte) 0x0D;
		line[6] = (byte) 0x0A;
		arrayLineSize = 7;
		// top
		line = fillTop(line, arrayLineSize);
		
		// content
		//byte[] printChange = new byte[3];
		if (type == 0) {
			template = fillTemplate(template, tags, data);
		} else {
			template = fillTemplateDiv(template, tags, sizeslastitem, data,
					indexini);
		}
		line = fillContent(line, arrayLineSize, template);
		
		// bottom
		line = fillBottom(line, arrayLineSize);
		
		mobileprint.SendBuffer(line, arrayLineSize);
		mobileprint.FeedLines(1);
	}
	
	
	
	
	
	
	
	
	
//|||||||||||||||||||||||||||END OF WORKING AREA||||||||||||||||||||||||||||||||||||||||||||||
	 	//gerar qualquer parte da nota de forma estática 
	public void genAllStatic(String[] template) throws UnsupportedEncodingException {
		// area útil de cada linha é de 45 caracteres, 43 se ficar um espaço
		// entre o inicio e o fim.
		byte[] line = new byte[10000];
		// type 0 = template sem barra divisória, e 1 com barra divisória

		// begin
		line[0] = (byte) 0x0D;// 0d
		line[1] = (byte) 0x0A;
		line[2] = (byte) 0x1B;
		line[3] = (byte) 0x33;
		// $ces Removidos - RESULTADO = Nenhuma alteração
		//line[5] = (byte) 0x1B;
		//line[6] = (byte) 0x21;
		//line[7] = (byte) 0x00;
		//
		line[4] = (byte) 0x06;
		line[5] = (byte) 0x0D;
		line[6] = (byte) 0x0A;
		arrayLineSize = 7;
		// top
		line = fillTop(line, arrayLineSize);
		// content
		line = fillContent(line, arrayLineSize, template);
		// bottom
		line = fillBottom(line, arrayLineSize);

		// 0d carriage/0a line feed
		mobileprint.SendBuffer(line, arrayLineSize);
		mobileprint.FeedLines(1);

	}

	
	public void genNotaByClassSample() throws UnsupportedEncodingException {
		// header
		getMobilePrinter().Reset();
		genNotaHeaderByFields(NfePrinterA7.templateHeader,
				NfePrinterA7.tagsHeader, new String[] { "Input Service LTDA",
						"652", "2" });
		// descrição
		StringBuilder valornfeStr = new StringBuilder();
		valornfeStr.append("         Danfe Simplificado          1-Saida    ");
		valornfeStr.append("         Documento Auxiliar da       NFe 617    ");
		valornfeStr.append("         Nota Fiscal Eletronica      Serie 2    ");
		valornfeStr.append("                                                ");
		try {
			getMobilePrinter().SendString(valornfeStr.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//

		// accesskey
		genAccessKey("1234567890123456789012345678901234567890123");

		// barcode
		genBarCode128HorNRI6("1234567890123456789012345678901234567890123");

		// natop - é necessário resetar a impressora após a impressão do código
		// de barras, pois é nesta função é colocado o texto em negrito
		getMobilePrinter().Reset();
		genAllByFields(NfePrinterA7.templateNatOp1,
				NfePrinterA7.tagsNatOp1,
				new String[] { "1Venda de Mercadoria, adquirida de", "terceiros" });

		// emitente
		// nfeprinter.getMobilePrinter().Reset();
		genAllByFields(NfePrinterA7.templateEmitter, NfePrinterA7.tagsEmitter,
				new String[] { "2Input Service LTDA", "Deputado",
						"Cotia - Sao Paulo", "07654", "09678", "98989", "999" });

		// destinatario
		// nfeprinter.getMobilePrinter().Reset();
		genAllByFields(NfePrinterA7.templateReceiver,
				NfePrinterA7.tagsReceiver, new String[] {
						"Distribuidora Imaginaria Ltda", "11-04-2012",
						"Av Nossa Senhora Aparecida N123", "Sao Paulo - SP",
						"11-04-2012", "54321-123", "11 9291 7463",
						"9999999999/0001-99", "18:35", "999 999 999 999" }, 1,
				NfePrinterA7.sizeslastitem, 1);

		// produtos
		// nfeprinter.getMobilePrinter().Reset();
		genNotaProductsByFields(
				NfePrinterA7.templateProduct1,
				NfePrinterA7.tagsTemplate1Content,
				NfePrinterA7.sizesTemplate1,
				new String[][] {
						{ "impressora portatil a7", "pc", "0010", "999,00",
								"980" },
						{ "Bobina de papel termico", "pc", "0100", "4", "500" },
						{ "Bobina de papel termico", "pc", "0100", "4", "500" } },
				"9999,00");//Não está sendo utilizado no momento
		// nfeprinter.disconnect();
	}
}
