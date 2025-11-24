package com.application.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ReportWindowView extends JFrame {
	private static final long serialVersionUID = 1L;
    public JButton exportButton;
    public JButton closeButton;
    public JTextArea textArea;
    public ChartPanel chartPanel;

    private final Color navyBlue = new Color(10, 61, 98);
    private final Color lightBackground = new Color(244, 246, 249);

    public ReportWindowView(List<Double> balances,
                            List<Double> rates,
                            List<String> labels) {

        setTitle("Variable Investor Report");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(lightBackground);

        // Title
        JLabel title = new JLabel("Investment Progression & Market Conditions", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(navyBlue);
        title.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(title, BorderLayout.NORTH);

        // Center: Chart + Legend
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        centerPanel.setBackground(lightBackground);

        chartPanel = new ChartPanel(balances, rates, labels);
        centerPanel.add(chartPanel, BorderLayout.CENTER);

        JPanel legendPanel = createLegendPanel(chartPanel.getCategoryColors());
        centerPanel.add(legendPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom: Text Area + Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(lightBackground);

        textArea = new JTextArea(8, 40);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(textArea);
        bottomPanel.add(scroll, BorderLayout.CENTER);

        exportButton = new JButton("Export Report");
        closeButton = new JButton("Close");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(lightBackground);
        buttonPanel.add(exportButton);
        buttonPanel.add(closeButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createLegendPanel(Map<String, Color> categoryColors) {
        JPanel legend = new JPanel();
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
        legend.setBackground(Color.WHITE);
        legend.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel legendTitle = new JLabel("Market Legend");
        legendTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        legendTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        legend.add(legendTitle);
        legend.add(Box.createVerticalStrut(10));

        for (Map.Entry<String, Color> entry : categoryColors.entrySet()) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
            row.setBackground(Color.WHITE);

            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(15, 15));
            colorBox.setBackground(entry.getValue());
            colorBox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

            JLabel label = new JLabel(entry.getKey());
            label.setFont(new Font("SansSerif", Font.PLAIN, 12));

            row.add(colorBox);
            row.add(label);

            legend.add(row);
        }

        return legend;
    }

    /**
     * Inner panel for drawing simple bar chart.
     */
    public static class ChartPanel extends JPanel {
    	private static final long serialVersionUID = 1L;
        private final List<Double> balances;
        private final List<Double> rates;
        private final List<String> labels;

        private final Color axisColor = new Color(80, 80, 80);
        private final java.util.Map<String, Color> categoryColors = new java.util.LinkedHashMap<>();

        public ChartPanel(List<Double> balances, List<Double> rates, List<String> labels) {
            this.balances = balances;
            this.rates = rates;
            this.labels = labels;

            setBackground(Color.WHITE);

            // define colors for each market category
            categoryColors.put("Bull Market", new Color(46, 204, 113));   // green
            categoryColors.put("Growth", new Color(52, 152, 219));        // blue
            categoryColors.put("Stable", new Color(149, 165, 166));       // grey
            categoryColors.put("Correction", new Color(243, 156, 18));    // orange
            categoryColors.put("Crash", new Color(231, 76, 60));          // red
        }

        public java.util.Map<String, Color> getCategoryColors() {
            return categoryColors;
        }

       
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (balances == null || balances.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            int leftMargin = 50;
            int bottomMargin = 40;
            int topMargin = 20;
            int rightMargin = 20;

            // Draw axes
            g2.setColor(axisColor);
            g2.drawLine(leftMargin, height - bottomMargin, width - rightMargin, height - bottomMargin); // X axis
            g2.drawLine(leftMargin, height - bottomMargin, leftMargin, topMargin); // Y axis

            // Find max balance
            double max = balances.stream().mapToDouble(Double::doubleValue).max().orElse(1.0);

            int n = balances.size();
            int barWidth = Math.max(10, (width - leftMargin - rightMargin) / (n * 2));

            for (int i = 0; i < n; i++) {
                double value = balances.get(i);
              
                String label = labels.get(i);

                double normalized = value / max;
                int barHeight = (int) ((height - bottomMargin - topMargin) * normalized);

                int x = leftMargin + (i * 2 * barWidth) + barWidth / 2;
                int y = height - bottomMargin - barHeight;

                Color barColor = categoryColors.getOrDefault(label, Color.GRAY);
                g2.setColor(barColor);
                g2.fillRect(x, y, barWidth, barHeight);

                // Year label
                g2.setColor(axisColor);
                String yearLabel = "Y" + (i + 1);
                int strWidth = g2.getFontMetrics().stringWidth(yearLabel);
                g2.drawString(yearLabel, x + (barWidth - strWidth) / 2, height - bottomMargin + 15);

                // Value label (optional, small)
                String valStr = String.format("%.0f", value);
                int valWidth = g2.getFontMetrics().stringWidth(valStr);
                g2.drawString(valStr, x + (barWidth - valWidth) / 2, y - 3);
            }
        }

		public List<Double> getRates() {
			return rates;
		}
    }
}
