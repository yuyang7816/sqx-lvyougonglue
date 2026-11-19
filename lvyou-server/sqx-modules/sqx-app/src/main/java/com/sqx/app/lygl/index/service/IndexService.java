package com.sqx.app.lygl.index.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.index.domain.bo.LineStatisticsBO;
import com.sqx.app.lygl.index.domain.bo.PayDetailsPageBo;
import com.sqx.app.lygl.index.domain.vo.MyDataVo;
import com.sqx.app.lygl.index.domain.bo.StatisticsBo;
import com.sqx.app.lygl.index.domain.vo.*;

import java.util.List;

/**
 * @author chengming
 * @since 2025/10/10
 */
public interface IndexService {
    MyDataVo myData(Long userId);

    IndexStatisticsVo indexStatistics();

    UserStatisticsVo userStatistics(StatisticsBo statisticsBo);


    PayStatisticsVo payStatistics(StatisticsBo statisticsBo);

    IPage<PayDetailsVo> payDetailPage(PayDetailsPageBo payDetailsPageBo);

    UserLineVO userLine(LineStatisticsBO lineStatisticsBO);

    List<ConcertStatisticsVO> concertStatistics(StatisticsBo statisticsBo);

    MomentStatisticsVO momentStatistics(StatisticsBo statisticsBo);

    ExamineStatisticsVO examineStatistics();
}
