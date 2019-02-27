package frc.robot.subsystems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;

// thieverized from blackbox
public class DataLogger {
  public Timer timer;
  private String name;
  private LinkedHashMap<String, Supplier<Object>> value_suppliers;
  boolean started = false;
  boolean started2 = false;
  File target_file;
  File target_file2;
  FileOutputStream file_out;
  FileOutputStream file_out2;
  long start_time;
  boolean usbExists = false;

  public static String[] getFileNames() {
    return new String[] {};
  }

  public DataLogger(String context_name) {
    this.timer = new Timer();
    this.name = context_name;
    value_suppliers = new LinkedHashMap<>();
    if(RobotBase.isSimulation()) {
      target_file = new File("./" + context_name + ".csv");
    }
    else {
      target_file = new File("/home/lvuser/" + context_name +".csv");
      //Log to usb as well
      target_file2 = new File("/media/sda1/" + context_name +".csv");
      this.usbExists = target_file2.getParentFile().exists();
    }
    target_file.getParentFile().mkdirs();
    add("time", () -> timer.get());
  }

  /**
   * Get the name of the Context. This, appended with .csv, will give you the Filename of the
   * Context CSV file.
   */
  public String getName() {
    return name;
  }

  /**
   * Add a Value Supplier to this context. The Value Supplier is a callback that is called whenever
   * the Context records data. This is used to gather the current value to record.
   * 
   * @param supplier_name The name of the supplier. This may not contain commas.
   * @param value_supplier The supplier to get the Number value from.
   */
  public void add(String supplier_name, Supplier<Object> value_supplier) {
    if (started)
      throw new IllegalStateException("Cannot add Suppliers after the Context has started!");
    this.value_suppliers.put(supplier_name, value_supplier);
  }

  /**
   * Tick the context. This will cause a single entry to be added to the Context CSV file. This
   * should be called on a regular basis, so it is recommended that this is called either in a
   * Heartbeat Listener, or during a State Tick.
   */
  public void log() {
    if (!started) {
      // First Run
      started = true;
      timer.start();
      start_time = (long) timer.get();
      try {
        System.out.println("Writing to roboRio");
        file_out = new FileOutputStream(target_file);
        file_out.write(String.join(",", value_suppliers.keySet()).getBytes());
        file_out.write('\n');
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    try {
      System.out.println("Writing to roboRio");
      file_out.write(String.join(",", value_suppliers.values().stream()
          .map(num -> num.get().toString()).collect(Collectors.toList())).getBytes());
      file_out.write('\n');
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.logToUSB();
  }

  public void logToUSB() {
    if (!started2) {
      // First Run
      started2 = true;
      //start_time = System.currentTimeMillis();
      if(this.usbExists){
        try {
          System.out.println("Writing to usb");
          file_out2 = new FileOutputStream(target_file2);
          file_out2.write(String.join(",", value_suppliers.keySet()).getBytes());
          file_out2.write('\n');
        } catch (IOException e) {
          e.printStackTrace();
          this.usbExists = false;
        }
        
      }
    }
    if(this.usbExists && file_out2 != null){
      try {
        System.out.println("Writing to usb");
        file_out2.write(String.join(",", value_suppliers.values().stream()
            .map(num -> num.get().toString()).collect(Collectors.toList())).getBytes());
        file_out2.write('\n');
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      if(!this.usbExists) {
        //System.out.println("this.usbExists is false");
      } else if(this.usbExists && file_out2 == null) {
        System.out.println("file_out2 is null");
      } else if(this.usbExists) {
        System.out.println("Something's seriously broken right now!");
      }
    }
  }
}