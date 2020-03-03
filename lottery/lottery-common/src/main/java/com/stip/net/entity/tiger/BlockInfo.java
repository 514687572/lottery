package com.stip.net.entity.tiger;

/**
 * 封装一条区块信息
 */
public class BlockInfo {
	private int block_number;// 区块号
	private String hash;// 区块哈希
	private long block_time;// 区块时间

	public BlockInfo(int block_number, String hash, long block_time) {
		this.block_number = block_number;
		this.hash = hash;
		this.block_time = block_time;
	}

	public long getBlock_number() {
		return block_number;
	}

	public void setBlock_number(int block_number) {
		this.block_number = block_number;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public long getBlock_time() {
		return block_time;
	}

	public void setBlock_time(long block_time) {
		this.block_time = block_time;
	}
}