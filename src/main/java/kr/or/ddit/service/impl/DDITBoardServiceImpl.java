package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.mapper.IDDITBoardMapper;
import kr.or.ddit.service.IDDITBoardService;
import kr.or.ddit.vo.DDITBoardVO;
import kr.or.ddit.vo.DDITTagVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Service
public class DDITBoardServiceImpl implements IDDITBoardService {

	@Inject
	IDDITBoardMapper boardMapper;
	
	@Override
	public ServiceResult insertForm(DDITBoardVO board) {
		ServiceResult result = null;
		int status = boardMapper.insertForm(board);
		
		if (status > 0) {	
			result = ServiceResult.OK; 
		} else {		
			result = ServiceResult.FAILED;	
		}
		return result;
	}

	@Override
	public void insertTags(DDITBoardVO board) {
		List<DDITTagVO> tagList = board.getTagList();
		
		for (int i = 0; i < tagList.size(); i++) {
			boardMapper.insertTags(tagList.get(i));
		}
	}

	@Override
	public DDITBoardVO selectDetail(int boNo) {
		boardMapper.increaseHit(boNo);
		return boardMapper.selectDetail(boNo);
	}

	@Override
	public int selectBoardCount(PaginationInfoVO<DDITBoardVO> pagingVO) {
		return boardMapper.selectBoardCount(pagingVO);
	}

	@Override
	public List<DDITBoardVO> selectList(PaginationInfoVO<DDITBoardVO> pagingVO) {
		return boardMapper.selectList(pagingVO);
	}

	@Override
	public ServiceResult modifyForm(DDITBoardVO board) {
		ServiceResult result = null;
		
		int status = boardMapper.modifyForm(board);
		
		if (status > 0) {
			result = ServiceResult.OK; 
		} else {			
			result = ServiceResult.FAILED;	
		}
		return result;
	}

	@Override
	public void modifyTags(DDITBoardVO board) {
		boardMapper.deleteTags(board.getBoNo());

		List<DDITTagVO> tagList = board.getTagList();
		
		for (int i = 0; i < tagList.size(); i++) {
			boardMapper.modifyTags(tagList.get(i));
		}
	}

	@Override
	public ServiceResult deleteForm(int boNo) {
		boardMapper.deleteTags(boNo);
		
		ServiceResult result = null;
		int status = boardMapper.deleteForm(boNo);
		
		if (status > 0) {	
			result = ServiceResult.OK; 
		} else {			
			result = ServiceResult.FAILED;	
		}
		return result;
	}

	@Override
	public void deleteTags(int boNo) {
		boardMapper.deleteTags(boNo);
	}

}
	