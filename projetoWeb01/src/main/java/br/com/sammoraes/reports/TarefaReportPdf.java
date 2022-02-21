package br.com.sammoraes.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.sammoraes.entities.Tarefa;
import br.com.sammoraes.entities.Usuario;

public class TarefaReportPdf {

	/*
	 * Método para retornar um relatório de tarefas em formato PDF
	 */
	public ByteArrayInputStream create(Date dataMin, Date dataMax, Usuario usuario, List<Tarefa> tarefas)
			throws Exception {

		// criando o documento PDF
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, out);

		// escrevendo o relatório
		document.open();

		// logotipo
		Image logo = Image.getInstance(new URL("https://i.ibb.co/0mJPXVG/Logo-Sam-Moraes-500-px-250-px-1.png"));
		document.add(logo);

		// título do relatório
		document.add(new Paragraph("Relatório de Tarefas", new Font(FontFamily.HELVETICA, 24)));
		document.add(new Paragraph("\n"));

		// Dados do usuário que gerou o relatório
		document.add(new Paragraph("Nome do usuário: " + usuario.getNome()));
		document.add(new Paragraph("Email: " + usuario.getEmail()));
		document.add(new Paragraph("\n"));

		// Período de consulta do relatório
		document.add(new Paragraph("Data de início: " + new SimpleDateFormat("dd/MM/yyyy").format(dataMin)));
		document.add(new Paragraph("Data de término: " + new SimpleDateFormat("dd/MM/yyyy").format(dataMax)));
		document.add(new Paragraph("\n"));

		// desenhando uma tabela para imprimir os dados das tarefas:
		PdfPTable table = new PdfPTable(5); // 5 colunas!
		table.setWidthPercentage(100); // 100% de largura

		// cabeçalhos da tabela
		table.addCell("NOME DA TAREFA");
		table.addCell("DATA");
		table.addCell("HORA");
		table.addCell("PRIORIDADE");
		table.addCell("DESCRIÇÃO");

		// imprimir o conteudo da tabela
		for (Tarefa t : tarefas) {

			table.addCell(t.getNome());
			table.addCell(new SimpleDateFormat("dd/MM/yyyy").format(t.getData()));
			table.addCell(t.getHora());
			table.addCell(t.getPrioridade());
			table.addCell(t.getDescricao());
		}

		// adicionar a tabela ao documento PDF
		document.add(table);

		document.add(new Paragraph("Quantidade de tarefas: " + tarefas.size()));

		// fechando o documento
		document.close();
		writer.close();

		// retornando o documento PDF
		return new ByteArrayInputStream(out.toByteArray());
	}
}
