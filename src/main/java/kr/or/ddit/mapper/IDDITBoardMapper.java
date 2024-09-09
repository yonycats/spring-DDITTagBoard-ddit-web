package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.DDITBoardVO;
import kr.or.ddit.vo.DDITTagVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IDDITBoardMapper {

	public int insertForm(DDITBoardVO board);

	public int insertTags(DDITTagVO dditTagVO);

	public DDITBoardVO selectDetail(int boNo);

	public int selectBoardCount(PaginationInfoVO<DDITBoardVO> pagingVO);

	public List<DDITBoardVO> selectList(PaginationInfoVO<DDITBoardVO> pagingVO);

	public void increaseHit(int boNo);

	public int modifyForm(DDITBoardVO board);

	public int deleteTags(int boNo);

	public int modifyTags(DDITBoardVO board);

	public int deleteForm(int boNo);

	public int modifyTags(DDITTagVO dditTagVO);


}
