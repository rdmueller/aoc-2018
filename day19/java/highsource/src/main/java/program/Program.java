package program;

import java.util.List;

import lombok.RequiredArgsConstructor;
import op.Op;
import register.Registers;

@RequiredArgsConstructor
public class Program {
	
	private final int instructionPointerRegister;
	private Registers registers = new Registers(0, 0, 0, 0, 0, 0);
	private final List<Op> ops;
	
	private int getInstructionPointer() {
		return registers.get(instructionPointerRegister);
	}
	
	private void increaseInstructionPointer() {
		final int ip = getInstructionPointer();
		registers = registers.set(instructionPointerRegister, ip + 1);
	}
	
	public Registers getRegisters() {
		return registers;
	}
	
	public boolean execute() {
		
		int ip = getInstructionPointer();
		
		if (ip < 0 || ip >= ops.size()) {
			throw new IllegalStateException();
//			return false;
		}
		
//		System.out.print("ip=");
//		System.out.print(StringUtils.rightPad(Integer.toString(ip), 2, ' '));
//		System.out.print("[");
//		System.out.print(registers.toString());
//		System.out.print("] ");
		
		Op op = this.ops.get(ip);
		
//		System.out.print(op);
//		System.out.print(' ');
		
		this.registers=op.apply(this.registers);
		
//		System.out.print("[");
//		System.out.print(registers.toString());
//		System.out.print("]");
//		System.out.println();
		
		increaseInstructionPointer();
		
		final int newIp = getInstructionPointer();
		if (newIp <0 || newIp >= ops.size()) {
			return false;
		}
		else {
			return true;
		}
	}
}
