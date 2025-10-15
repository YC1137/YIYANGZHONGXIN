    package com.neuedu.yyzx.controller;


    import com.neuedu.yyzx.dto.BackDownDTO;
    import com.neuedu.yyzx.pojo.BackDown;
    import com.neuedu.yyzx.pojo.Bed;
    import com.neuedu.yyzx.pojo.Customer;
    import com.neuedu.yyzx.service.BackDownService;
    import com.neuedu.yyzx.service.BedService;
    import com.neuedu.yyzx.service.CustomerService;
    import com.neuedu.yyzx.utils.ResultVo;
    import io.swagger.annotations.Api;
    import io.swagger.annotations.ApiOperation;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/backDown")
    @Api(tags = "出院管理")
    @CrossOrigin
    public class BackDownController {
        @Autowired
        private BackDownService backDownService;
        @Autowired
        private BedService bedService;
        @Autowired
        private CustomerService customerService;

        @ApiOperation("展示退住申请")
        @GetMapping("/listBackDown")
        public ResultVo listBackDown(BackDownDTO backDownDTO) throws Exception {
            return ResultVo.ok(backDownService.selectBackDownVo(backDownDTO));
        }

        @ApiOperation("添加退住申请")
        @PostMapping("/addBackDown")
        public ResultVo addBackDown(BackDown backDown) throws Exception {
            backDownService.save(backDown);
            return ResultVo.ok("添加成功");
        }

        @ApiOperation("审批退住")
        @PostMapping("/examineBackDown")
        @Transactional(rollbackFor = Exception.class)
        public ResultVo examineBackDown(BackDown backDown) throws Exception {
            Customer customer = customerService.getById(backDown.getCustomerId());
            Bed bed = new Bed();
            bed.setId(customer.getBedId());
            bed.setBedStatus(1);
            boolean temp = bedService.updateById(bed);
            if(!temp){
                throw new Exception("退住失败");
            }
            return backDownService.examineBackDown(backDown);
        }

        @ApiOperation("删除退住申请")
        @GetMapping("/delBackDown")
        public ResultVo delBackDown(Integer id) throws Exception {
            return backDownService.delBackDown(id);
        }
    }
