package peripheral

import chisel3._
import chisel3.util._
import riscv.Parameters



/*
    Just a simple timer with preset count and output signal every fixed period.
*/
class SimpleTimer(freq: Int) extends Module {
  val io = IO(new Bundle {
    val signal_interrupt = Output(Bool())

    val debug_limit = Output(UInt(Parameters.DataWidth))
    val debug_enabled = Output(Bool())
  })


  val count = RegInit(0.U(32.W))
  val limit = RegInit(freq.U(32.W))
  io.debug_limit := limit
  val enabled = RegInit(true.B)
  io.debug_enabled := enabled

  io.signal_interrupt := enabled && (count >= ((limit - 10.U) >> 1))

  when(count >= limit) {
    count := 0.U
  }.otherwise {
    count := count + 1.U
  }
}