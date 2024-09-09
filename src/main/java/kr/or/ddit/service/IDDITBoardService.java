package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.DDITBoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IDDITBoardService {

	public ServiceResult insertForm(DDITBoardVO board);

	public void insertTags(DDITBoardVO board);

	public DDITBoardVO selectDetail(int boNo);

	public int selectBoardCount(PaginationInfoVO<DDITBoardVO> pagingVO);

	public List<DDITBoardVO> selectList(PaginationInfoVO<DDITBoardVO> pagingVO);

	public ServiceResult modifyForm(DDITBoardVO board);

	public void modifyTags(DDITBoardVO board);

	public ServiceResult deleteForm(int boNo);

	public void deleteTags(int boNo);
}
