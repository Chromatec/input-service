package com.br.inputservice.a7printertest;

import inputservice.printerLib.BoletoPrinter;
import inputservice.printerLib.BoletoUtils;
import inputservice.printerLib.NfePrinterA7;
import inputservice.printerLib.ReceiptPrinterA7;

import java.io.UnsupportedEncodingException;
import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Runnable {
	static private Button btnNfe, btnBoleto, btnRec;// btnMeuBoleto;
	public static NfePrinterA7 nfeprinter;
	public static BoletoPrinter boletoprinter;
	public static ReceiptPrinterA7 receiptprinter;
	private static BoletoUtils boleto;
	private static int btntype = 0;
	private static TextView status;
	private static boolean connected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nfeprinter = new NfePrinterA7();
		boletoprinter = new BoletoPrinter();
		receiptprinter = new ReceiptPrinterA7();
		setButtonsClickListener();

		status = (TextView) findViewById(R.id.status);
	}

	private void setButtonsClickListener() {
		setBtnPrintNFClickListener();
		setBtnPrintBolClickListener();
		setBtnPrintRecClickListener();
	/*	setBtnMeuBoletoClickListener();*/
	}

	/*
	 * private void setBtnMeuBoletoClickListener() { btnMeuBoleto = (Button)
	 * findViewById(R.id.BtnMeuBoleto); btnMeuBoleto.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { Intent i = new
	 * Intent(MainActivity.this, BoletoActivity.class); startActivity(i);
	 * 
	 * } });
	 * 
	 * }
	 */

	private void setBtnPrintNFClickListener() {
		btnNfe = (Button) findViewById(R.id.btnNfe);
		btnNfe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					genNotaByClassA7();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	private void setBtnPrintBolClickListener() {
		btnBoleto = (Button) findViewById(R.id.btnBoleto);
		btnBoleto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				genBoletoByClassA7();
			}
		});

	}

	private void setBtnPrintRecClickListener() {
		btnRec = (Button) findViewById(R.id.btnRecibo);
		btnRec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				genReciboByClassA7();
			}
		});

	}
//----------------------------------------------------------------------------------Nota A7
	public void genNotaByClassA7() throws UnsupportedEncodingException {
		connected = nfeprinter.connect(false);
		if (connected) {
			status = (TextView) findViewById(R.id.status);
			status.setText(status.getText().toString()
					.replace("Desconectado", "Conectado"));
			enabledButtons(false);
			
			// #Impressão
			// header
			nfeprinter.getMobilePrinter().Reset();
			nfeprinter.printBar();
			nfeprinter.getMobilePrinter().Reset();
			nfeprinter.printCarinhaFeliz();
			nfeprinter.getMobilePrinter().Reset();
			nfeprinter.genNotaHeaderByFields(NfePrinterA7.templateHeader,
					NfePrinterA7.tagsHeader, new String[] {"Input Service LTDA", "652", "2" });
			/*
			// descrição
			StringBuilder valornfeStr = new StringBuilder();
			valornfeStr
				.append("         			Danfe Simplificado          1-Saida    ");
			valornfeStr
				.append("         			Documento Auxiliar da       NFe 617    ");
			valornfeStr
				.append("         			Nota Fiscal Eletronica      Serie 2    ");
			valornfeStr
				.append("           	                                           ");
			try {
				nfeprinter.getMobilePrinter()
						.SendString(valornfeStr.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// accesskey
			nfeprinter
					.genAccessKey("1234567890123456789012345678901234567890123");

			// barcode
			nfeprinter
					.genBarCode128HorNRI6("1234567890123456789012345678901234567890123");

			// natop - é necessário resetar a impressora após a impressão do
			// código de barras, pois nesta função é colocado o texto em
			// negrito
			nfeprinter.getMobilePrinter().Reset();
			nfeprinter.genAllByFields(NfePrinterA7.templateNatOp1,
					NfePrinterA7.tagsNatOp1, new String[] {
					"Venda de Mercadoria, adquirida de", "terceiros" });
			
			
			// emitente
			// nfeprinter.getMobilePrinter().Reset();
			nfeprinter.genAllByFields(NfePrinterA7.templateEmitter,
					NfePrinterA7.tagsEmitter, new String[] {
							"Input Service LTDA", "Deputado",
							"Cotia - Sao Paulo", "07654", "09678", "98989",
							"999999999" });

			// destinatario
			// nfeprinter.getMobilePrinter().Reset();

			nfeprinter.genAllByFields(NfePrinterA7.templateReceiver,
					NfePrinterA7.tagsReceiver, new String[] {
							"123456789.123456789.123456789.123", "11-04-2012",
							"123456789.123456789.123456789.123",
							"123456789.123456789.123456789.123", "11-04-2012", "123456789",
							"123456789.12345", "123456789.123456789.12345678", "18:35",
							"123456789.123456789.123456789." }, 1, NfePrinterA7.sizeslastitem,
					1);*/

			// produtos
			//nfeprinter.getMobilePrinter().Reset();
			
			//MÉTODO UTILIZADO PARA A IMPRESSÃO DA PARTE DE PRODUTO
			
			//" COD |      Produto      |CST|un|qtde|Val.Un|Descnt|Val.Tot| ICMS  |Al",
			//5, 19, 3, 2, 4, 6, 6, 7, 7, 2
/*			nfeprinter.genNotaProductsByFields(NfePrinterA7.templateProduct1,
					NfePrinterA7.tagsTemplate1Content, NfePrinterA7.sizesTemplate1,
					new String[][] {
								{ "12345", "123456789.123456789", "123", "12", "1234", "123456","123456","1234567", "1234567", "12" },
								{ "12345", "123456789.123456789", "123", "12", "1234", "123456","123456","1234567", "1234567", "12" },
								{ "12345", "123456789.123456789", "123", "12", "1234", "123456","123456","1234567", "1234567", "12" },
								{ "12345", "123456789.123456789", "123", "12", "1234", "123456","123456","1234567", "1234567", "12" },
								{ "12345", "123456789.123456789", "123", "12", "1234", "123456","123456","1234567", "1234567", "12" }
							}, "");
			nfeprinter.genNotaProductsByFields(NfePrinterA7.templateProduct2,
					NfePrinterA7.tagsTemplate2Content, NfePrinterA7.sizesTemplate2,
					new String[][] {
								{ "impressora portatil a72", "pc", "0010", "999,00", "100,00", "060","45,60","980,00" },
								{ "Bobina de papel termico2", "pc", "0100","  4,00", "2,0", "060", "45,60", "1234567,00" },
								{ "Bobina de papel termico2", "pc", "0999","  4,00", "10,0", "060","45,60", "500,00"},
								{ "Bobina de papel2", "pc", "0260","  4,00", "96,74", "060","45,60", "500,00"},
								{ "Papel de teste2", "ea", "0260"," 14,00", "91,00", "000","04,60", "100,00"}
							}, "");
			nfeprinter.genNotaProductsByFields(NfePrinterA7.templateProduct3,
					NfePrinterA7.tagsTemplate3Content, NfePrinterA7.sizesTemplate3,
					new String[][] {
								{ "1234567890", "1234567890", "1234567890", "1234567", "1234567890"},
							}, "0123,00");
			
			// descrição
			nfeprinter.getMobilePrinter().Reset();
			valornfeStr = new StringBuilder();
			valornfeStr.append("\n\n\n\n\n");
			try {
				nfeprinter.getMobilePrinter()
						.SendString(valornfeStr.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// */

			// foram incluidas estas linhas no if
			btntype = 0;
			Thread currentThread = new Thread(this);
			currentThread.start();
		} else {
			Toast.makeText(this,
					"Não foi possível realizar a conexão com a impressora!",
					Toast.LENGTH_LONG).show();
		}
	}

	public static void enabledButtons(boolean enabled) {
		btnNfe.setEnabled(enabled);
		btnNfe.setClickable(enabled);
		btnBoleto.setEnabled(enabled);
		btnBoleto.setClickable(enabled);
		btnRec.setEnabled(enabled);
		btnRec.setClickable(enabled);
	}

//----------------------------------------------------------------------------Boleto A7
	public void genBoletoByClassA7() {
		connected = boletoprinter.connect(false);
		if (connected) {
			status = (TextView) findViewById(R.id.status);
			status.setText(status.getText().toString()
					.replace("Desconectado", "Conectado"));
			// btnPrint.setEnabled(false);
			enabledButtons(false);

			boleto = new BoletoUtils();
			boleto.setLinhaDigitavel("23792.38401 61130.370598 68001.271805 1 56220000320000");
			boleto.setNomeBanco("Bradesco Teste");
			boleto.setCodBanco("237-2");
			boleto.setLocalPagamento("Banco Bradesco S.A.");
			boleto.setLocalOpcionalPagamento("Pagavel preferencialmente na rede bradesco ou banco postal.");
			boleto.setVencimento("27/02/2013");
			boleto.setCedente("Input Service Informatica Ltda");
			boleto.setAgenciaCodigoCedente("02384-1 / 0012718-3");
			boleto.setDatadocumento("06/02/2013");
			boleto.setNumeroDocumento("00000TESTE");
			boleto.setEspecieDoc("OU");
			boleto.setAceite("Nao");
			boleto.setDataProcessameto("06/02/2013");
			boleto.setNossoNumero("06/11/303705968-5");
			boleto.setUsoDoBanco("08650");
			boleto.setCip("000");
			boleto.setCarteira("06");
			boleto.setEspecieMoeda("R$");
			boleto.setQuantidade("");
			boleto.setValor("");
			boleto.setValorDocumento("3.200,00");
			boleto.setInstrucoesCedente(new String[] {
					"Instrucoes de responsabilidade do cedente    *** Valores expressos em R$ ***",
					"", "", "", "", "", "" });
			boleto.setDesconto(" -200,00");
			boleto.setDeducoes("  -10,00");
			boleto.setMulta("   50,00");
			boleto.setAcrescimos("   10,00");
			boleto.setValorCobrado("3.050,00");
			boleto.setSacadoNome("INPUT SERVICE INFORMATCA LTDA");
			boleto.setSacadoEndereco("RUA DEPUTADO MIGUEL PETRELLI, 355");
			boleto.setSacadoCep("06705-442");
			boleto.setSacadoCidade("COTIA");
			boleto.setSacadoUF("SP");
			boleto.setSacadoCnpj("061.557.856/0001-57");

			// inicio uma outrathread para imprimir o boleto
			Thread printerThread = new Thread() {
				// setting the behavior we want from the Thread
				@Override
				public void run() {
					threadHandlerBoleto.sendEmptyMessage(0);
				}
			};
			printerThread.start();

			btntype = 1;
			Thread currentThread = new Thread(this);
			currentThread.start();

		} else {
			Toast.makeText(this,
					"Não foi possível realizar a conexão  com a impressora!",
					Toast.LENGTH_LONG).show();
		}

	}
//----------------------------------------------------------------------------Recibo A7
	public void genReciboByClassA7() {
		connected = receiptprinter.connect(false);
		if (connected) {
			status = (TextView) findViewById(R.id.status);
			status.setText(status.getText().toString()
					.replace("Desconectado", "Conectado"));
			// btnPrint.setEnabled(false);
			enabledButtons(false);

			// teste recibo
			
			// template igual ao padrão ReceiptPrinterA8.template
			final String[] template = new String[] { "Protocolo: #prot",
					"Fornecedora: #forne", "CNPJ: #cnpj", "           ",
					"Cliente: #cliente", "#2cliente", "CNPJ: #2cnpj",
					"Endereco: #end", "#2end", "     ",
					"Produto                              Quantidade",
					"#prod                #qtd",
					"                            ",
					"Condicao de pagamento: #cond", "Recebido por: #rec",
					"Identificacao do Recebedor: #ide",
					"                                  ", "Data: #data",
					"Hora: #hora", "           ", "Assinatura do cliente",
					"                     ", "....................." };
			// tags presentes no template sem as duas de produto, pois elas
			// serão usadas internamente pelo método
			final String[] tags = { "#prot", "#forne", "#cnpj", "#cliente",
					"#2cliente", "#2cnpj", "#end", "#2end", "#cond", "#rec",
					"#ide", "#data", "#hora" };
			// dados correspondentes as tags
			final String[] data = { "0000", "Moggiana Ltda",
					"123.456.789/0001-99",
					"DRISERVE EMPRESA MINERACAO FONTES AGUA",
					"S MINERAL LTDA EPP", "00000000016063",
					"AV JACU-PESSEGO, 4710", "VILA JACUI SAO PAULO SP BRASIL",
					"A VISTA", "JOSE", "TESTE", "13-03-2013", "15:06" };
			// igual ao ReceiptPrinterA8.testDataProduct - contém o nome e
			// quantidade do produto, o nome irá ser dividido em várias linhas
			// se ultrapassar 20 caracteres q é o tamanho do campo nome
			final String[][] testDataProduct = { { "Agua Mineral", "50" },
					{ "agua agua agua agua agua agua", "20" } };
			// método que recebe o template, as tags e dados correspondentes, o
			// índice da linha que contém as tags de produto e os dados a
			// preencher os produtos
			receiptprinter.genAllByFields(template, tags, data, 11,
					testDataProduct);

			// inicio uma outrathread para imprimir o boleto
			Thread printerThread = new Thread() {
				// setting the behavior we want from the Thread
				@Override
				public void run() {
					threadHandlerReceipt.sendEmptyMessage(0);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					threadHandlerReceipt.sendEmptyMessage(1);
				}
			};
			printerThread.start();

		} else {
			Toast.makeText(this,
					"Não foi possível realizar a conexão com a impressora!",
					Toast.LENGTH_LONG).show();
		}
	}
	//----------------------------------------------------------------------------Recibo A8
	public void genReciboByClassA8() {
		connected = receiptprinter.connect(false);
		if (connected) {
			status = (TextView) findViewById(R.id.status);
			status.setText(status.getText().toString()
					.replace("Desconectado", "Conectado"));
			// btnPrint.setEnabled(false);
			enabledButtons(false);

			// teste recibo

			// template igual ao padrão ReceiptPrinterA8.template
			final String[] template = new String[] { "Protocolo: #prot",
					"Fornecedora: #forne", "CNPJ: #cnpj", "           ",
					"Cliente: #cliente", "#2cliente", "CNPJ: #2cnpj",
					"Endereco: #end", "#2end", "     ",
					"Produto              Quantidade", "#prod    #qtd",
					"                            ",
					"Condicao de pagamento: #cond", "Recebido por: #rec",
					"Identificacao do Recebedor: #ide",
					"                                  ", "Data: #data",
					"Hora: #hora", "           ", "Assinatura do cliente",
					"                     ", "....................." };
			// tags presentes no template sem as duas de produto, pois elas
			// serão usadas internamente pelo método
			final String[] tags = { "#prot", "#forne", "#cnpj", "#cliente",
					"#2cliente", "#2cnpj", "#end", "#2end", "#cond", "#rec",
					"#ide", "#data", "#hora" };
			// dados correspondentes as tags
			final String[] data = { "0000", "Moggiana Ltda",
					"123.456.789/0001-99", "DRISERVE EMPRESA MINERA",
					"CAO FONTES AGUAS MINERAL LTDA EPP", "00000000016063",
					"AV JACU-PESSEGO, 4710", "VILA JACUI SAO PAULO SP BRASIL",
					"A VISTA", "JOSE", "TESTE", "13-03-2013", "15:06" };
			// igual ao ReceiptPrinterA8.testDataProduct - contém o nome e
			// quantidade do produto, o nome irá ser dividido em várias linhas
			// se ultrapassar 20 caracteres q é o tamanho do campo nome
			final String[][] testDataProduct = { { "Agua Mineral", "50" },
					{ "agua agua agua agua agua agua", "20" } };
			// método que recebe o template, as tags e dados correspondentes, o
			// índice da linha que contém as tags de produto e os dados a
			// preencher os produtos
			receiptprinter.genAllByFields(template, tags, data, 11,
					testDataProduct);

			// inicio uma outrathread para imprimir o boleto
			Thread printerThread = new Thread() {
				// setting the behavior we want from the Thread
				@Override
				public void run() {
					threadHandlerReceipt.sendEmptyMessage(0);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					threadHandlerReceipt.sendEmptyMessage(1);
				}
			};
			printerThread.start();

		} else {
			Toast.makeText(this,
					"Não foi possível realizar a conexão com a impressora!",
					Toast.LENGTH_LONG).show();
		}
	}

	// manages Threads messages of Boleto
	static private Handler threadHandlerBoleto = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// handling messages and acting for the Thread goes here
			boletoprinter.getMobilePrinter().Reset();
			boletoprinter.printBoleto(boleto);
		}
	};

	// manages Threads messages of Receipt
	static private Handler threadHandlerReceipt = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// handling messages and acting for the Thread goes here
			if (msg.what == 1) {
				enabledButtons(true);
			} else {
				while (receiptprinter.getMobilePrinter().QueryPrinterStatus() != 0) {
				}
				receiptprinter.disconnect();
			}
		}
	};

	@Override
	public void run() {
		threadHandler.sendEmptyMessage(0);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadHandler.sendEmptyMessage(1);
	}

	static private Handler threadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//
			if (btntype == 1) {
				if (msg.what == 1) {
					enabledButtons(true);
				} else {
					while (boletoprinter.getMobilePrinter()
							.QueryPrinterStatus() != 0) {
					}
					boletoprinter.disconnect();
				}
			} else {
				if (msg.what == 1) {
					enabledButtons(true);
				} else {
					while (nfeprinter.getMobilePrinter().QueryPrinterStatus() != 0) {
					}
					nfeprinter.disconnect();
				}
			}
			status.setText(status.getText().toString()
					.replace("Conectado", "Desconectado"));
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
