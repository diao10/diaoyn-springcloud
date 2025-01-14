export interface BaseDto {
  createTime: string;
  updateTime?: string | null;
  deleteTime?: string | null;
  createUser?: string | null;
  deleteUser?: string | null;
}

export interface Result<T = any> {
  code: number;
  result: T;
  message: string;
  success: boolean;
  timestamp: number;
}
