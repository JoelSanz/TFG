package com.Game.core;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;


public class DPSChart extends JPanel {
    JPanel pane;
    ChartPanel chartPanel;
    int CELL_SIZE;
    private static final Random random = new Random();
    DynamicTimeSeriesCollection dataset;
    float[] damage;
    double upperRange = 9000;
    private Timer timer;
    JFreeChart chart;
    BufferedImage background;

    public DPSChart(String title, int cs) {

        CELL_SIZE = cs;
        pane = new JPanel();
        // Create dataset
        //addData(5);
        dataset = createDataset();
        // Create chart

        chart = createChart(title, dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(CELL_SIZE * 22, CELL_SIZE * 6));
        pane.add(chartPanel);
        pane.setBounds(CELL_SIZE, CELL_SIZE * 20, CELL_SIZE * 22, CELL_SIZE * 6);
        timer = new Timer(100, new ActionListener() {
            float[] newData = new float[1];

            @Override
            public void actionPerformed(ActionEvent e) {
                dataset.advanceTime();
                dataset.appendData(damage);
            }
        });

    }

    private DynamicTimeSeriesCollection createDataset() {



        DynamicTimeSeriesCollection dataset = new DynamicTimeSeriesCollection(1, 120, new Second());
        dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));

        dataset.addSeries(StartingChart(), 0, "Damage");

        return dataset;
    }

    private JFreeChart createChart(String title, final XYDataset dataset){
        final JFreeChart res = ChartFactory.createTimeSeriesChart(title, "Time", "Damage",dataset, false, false, false);
        final XYPlot plot =  res.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setRange(0, upperRange);
        return res;
    }

   public void addData(float dmg){
       timer.start();
       float[] newData = new float[1];
       newData[0] = damage[0]+dmg;
       damage[0] = newData[0];
       if(damage[0]>=upperRange) {
           upperRange += 9000;
           chart.getXYPlot().getRangeAxis().setRange(0, upperRange);
       }

   }



   private float[] StartingChart(){
        damage = new float[1];
       for (int i = 0; i < damage.length; i++) {
           damage[i] = 0;
       }
        return damage;
   }
    public JPanel getPane() {
        return pane;
    }

}
